package uk.co.alt236.apk.repo.signature.v2;

import com.android.apksig.internal.apk.v2.V2SchemeVerifier;
import uk.co.alt236.apk.repo.signature.SigningCertificate;
import uk.co.alt236.apk.util.StreamUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SignatureV2Repository {
    private final SignatureValidator certificateExtractor;
    private final RandomAccessFile file;
    private final List<SigningCertificate> certificates;
    private boolean isSigned;
    private boolean isSignatureValid;

    public SignatureV2Repository(final File file) {
        certificateExtractor = new SignatureValidator();

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


    private synchronized void loadCertificates() {
        if (!certificates.isEmpty()) {
            return;
        }

        try {
            final SignatureValidator.Result result = certificateExtractor.extractCertificates(file);
            certificates.addAll(result.getCertificates());
            isSigned = !certificates.isEmpty();
            isSignatureValid = !result.containsErrors();
        } catch (V2SchemeVerifier.SignatureNotFoundException e) {
            isSigned = false;
            isSignatureValid = false;
        }
    }

}
