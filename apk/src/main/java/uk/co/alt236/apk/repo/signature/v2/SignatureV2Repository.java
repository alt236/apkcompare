package uk.co.alt236.apk.repo.signature.v2;

import com.android.apksig.apk.ApkFormatException;
import com.android.apksig.apk.ApkUtils;
import com.android.apksig.internal.apk.v2.V2SchemeVerifier;
import com.android.apksig.internal.util.RandomAccessFileDataSource;
import com.android.apksig.util.DataSource;
import com.android.apksig.zip.ZipFormatException;
import uk.co.alt236.apk.repo.signature.SigningCertificate;
import uk.co.alt236.apk.util.StreamUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SignatureV2Repository {
    private final RandomAccessFile file;
    private final List<SigningCertificate> certificates;
    private boolean isSigned;
    private boolean isSignatureValid;

    public SignatureV2Repository(final File file) {
        certificates = new ArrayList<>();

        try {
            this.file = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public List<SigningCertificate> getCertificates() {
        loadCertificates();
        return Collections.unmodifiableList(certificates);
    }

    private synchronized void loadCertificates() {
        if (!certificates.isEmpty()) {
            return;
        }

        final DataSource dataSource = new RandomAccessFileDataSource(file);
        try {
            final ApkUtils.ZipSections zipSections = ApkUtils.findZipSections(dataSource);
            final V2SchemeVerifier.Result v2Result = V2SchemeVerifier.verify(new RandomAccessFileDataSource(file), zipSections);
            certificates.addAll(extractCertificates(v2Result));
            isSigned = !certificates.isEmpty();
            isSignatureValid = !v2Result.containsErrors();
        } catch (IOException
                | ZipFormatException
                | NoSuchAlgorithmException
                | ApkFormatException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } catch (V2SchemeVerifier.SignatureNotFoundException e) {
            isSigned = false;
            isSignatureValid = false;
        }
    }

    public boolean isSigned() {
        loadCertificates();
        return isSigned;
    }

    public boolean isSignatureValid() {
        loadCertificates();
        return isSignatureValid;
    }

    public void close() {
        StreamUtils.close(file);
    }

    private List<SigningCertificate> extractCertificates(V2SchemeVerifier.Result result) {
        final List<SigningCertificate> retVal = new ArrayList<>();


        for (final V2SchemeVerifier.Result.SignerInfo signerInfo : result.signers) {
            for (final X509Certificate cert : signerInfo.certs) {
                retVal.add(new SigningCertificate(cert));
            }
        }

        return retVal;
    }
}
