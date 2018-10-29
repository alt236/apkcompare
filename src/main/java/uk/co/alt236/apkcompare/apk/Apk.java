package uk.co.alt236.apkcompare.apk;

import uk.co.alt236.apkcompare.repo.dex.DexRepository;
import uk.co.alt236.apkcompare.repo.dex.model.DexClass;
import uk.co.alt236.apkcompare.repo.signature.SignatureRepository;
import uk.co.alt236.apkcompare.repo.signature.SigningCertificate;
import uk.co.alt236.apkcompare.repo.smali.SmaliRepository;
import uk.co.alt236.apkcompare.zip.common.Entry;
import uk.co.alt236.apkcompare.zip.common.ZipContents;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

public class Apk {
    private final ZipContents zipContents;
    private final File file;
    private final SignatureRepository signatureRepository;
    private final DexRepository dexRepository;
    private final SmaliRepository smaliRepository;

    Apk(File file,
        ZipContents zipContents,
        SignatureRepository signatureRepository,
        DexRepository dexRepository, SmaliRepository smaliRepository) {

        this.file = file;
        this.zipContents = zipContents;
        this.signatureRepository = signatureRepository;
        this.dexRepository = dexRepository;
        this.smaliRepository = smaliRepository;
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

    @Nullable
    public Entry getEntry(String name) {
        return zipContents.getEntry(name);
    }

    public InputStream getEntryStream(@Nonnull Entry entry) {
        return zipContents.getInputStream(entry);
    }

    public Set<DexClass> getClasses() {
        return dexRepository.getAllClasses();
    }

    @Nullable
    public DexClass getClassByType(String classType) {
        return dexRepository.getClassByType(classType);
    }

    public String getSmaliForClassType(final String classType) {
        return smaliRepository.getSmaliForType(classType);
    }
}
