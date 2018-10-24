package uk.co.alt236.apkcompare.apk;

import uk.co.alt236.apkcompare.repo.signature.SignatureRepository;
import uk.co.alt236.apkcompare.repo.signature.SigningCertificate;
import uk.co.alt236.apkcompare.zip.common.Entry;
import uk.co.alt236.apkcompare.zip.common.ZipContents;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.InputStream;
import java.util.List;

public class Apk {
    private final ZipContents zipContents;
    private final File file;
    private final SignatureRepository signatureRepository;

    Apk(File file, ZipContents zipContents, SignatureRepository signatureRepository) {
        this.file = file;
        this.zipContents = zipContents;
        this.signatureRepository = signatureRepository;
    }

    public File getFile() {
        return file;
    }

    public List<Entry> getContents() {
        return zipContents.getEntries();
    }

    public List<SigningCertificate> getCertificates() {
        return signatureRepository.getCertificates();
    }

    public void close() {
        zipContents.close();
    }

    public Entry getEntry(String name) {
        return zipContents.getEntry(name);
    }

    public InputStream getEntryStream(@Nonnull Entry entry) {
        return zipContents.getInputStream(entry);
    }
}
