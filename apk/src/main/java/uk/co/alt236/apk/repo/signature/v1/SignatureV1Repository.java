package uk.co.alt236.apk.repo.signature.v1;

import uk.co.alt236.apk.repo.signature.SignatureStatus;
import uk.co.alt236.apk.repo.signature.SigningCertificate;
import uk.co.alt236.apk.repo.signature.ValidationResult;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class SignatureV1Repository {
    private final File file;
    private final List<SigningCertificate> certList;
    private ValidationResult validationResult;

    public SignatureV1Repository(File file) {
        this.file = file;
        this.certList = new ArrayList<>();
    }

    public List<SigningCertificate> getCertificates() {
        loadCertificates();
        return Collections.unmodifiableList(certList);
    }

    public boolean isSigned() {
        validateSignature();
        return validationResult.getSignatureStatus() != SignatureStatus.ABSENT
                && validationResult.getSignatureStatus() != SignatureStatus.ERROR;

    }

    public boolean isSignatureValid() {
        validateSignature();
        return validationResult.getSignatureStatus() == SignatureStatus.VALID;
    }

    private void loadCertificates() {
        if (!certList.isEmpty()) {
            return;
        }

        final List<SigningCertificate> retVal = new ArrayList<>();

        try (final ZipFile zipFile = new ZipFile(file)) {
            final Enumeration<? extends ZipEntry> iterator = zipFile.entries();
            final List<ZipEntry> certEntries = new ArrayList<>();

            while (iterator.hasMoreElements()) {
                final ZipEntry ze = iterator.nextElement();
                final String name = ze.getName();
                if (name.startsWith("META-INF/") && (name.endsWith(".RSA") || name.endsWith(".DSA"))) {
                    certEntries.add(ze);
                }
            }

            for (final ZipEntry zipEntry : certEntries) {
                final InputStream is = zipFile.getInputStream(zipEntry);
                final CertificateFactory factory = CertificateFactory.getInstance("X.509");
                final CertPath cp = factory.generateCertPath(is, "PKCS7");
                for (final Certificate certificate : cp.getCertificates()) {
                    retVal.add(new SigningCertificate((X509Certificate) certificate));
                }
                is.close();
            }
        } catch (IOException | CertificateException e) {
            e.printStackTrace();
        }

        certList.addAll(retVal);
    }


    private synchronized void validateSignature() {
        if (validationResult != null) {
            return;
        }

        validationResult = new SignatureValidator().validateSignature(file);
    }

}
