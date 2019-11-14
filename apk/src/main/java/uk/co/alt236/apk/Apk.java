package uk.co.alt236.apk;

import uk.co.alt236.apk.repo.dex.DexRepository;
import uk.co.alt236.apk.repo.dex.model.DexClass;
import uk.co.alt236.apk.repo.dex.model.DexClassType;
import uk.co.alt236.apk.repo.signature.SigningCertificate;
import uk.co.alt236.apk.repo.signature.v1.SignatureV1Repository;
import uk.co.alt236.apk.repo.signature.v2.SignatureV2Repository;
import uk.co.alt236.apk.repo.smali.SmaliRepository;
import uk.co.alt236.apk.zip.Entry;
import uk.co.alt236.apk.zip.ZipContents;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

public class Apk {
    private final ZipContents zipContents;
    private final File file;
    private final SignatureV1Repository signatureV1Repository;
    private final SignatureV2Repository signatureV2Repository;
    private final DexRepository dexRepository;
    private final SmaliRepository smaliRepository;

    Apk(File file,
        ZipContents zipContents,
        SignatureV1Repository signatureV1Repository,
        SignatureV2Repository signatureV2Repository,
        DexRepository dexRepository,
        SmaliRepository smaliRepository) {

        this.file = file;
        this.zipContents = zipContents;
        this.signatureV1Repository = signatureV1Repository;
        this.signatureV2Repository = signatureV2Repository;
        this.dexRepository = dexRepository;
        this.smaliRepository = smaliRepository;
    }

    public File getFile() {
        return file;
    }

    public List<Entry> getContents() {
        return zipContents.getEntries();
    }

    public List<SigningCertificate> getV1Certificates() {
        return signatureV1Repository.getCertificates();
    }

    public boolean isV1Signed() {
        return signatureV1Repository.isSigned();
    }

    public boolean isV1SignatureValid() {
        return signatureV1Repository.isSignatureValid();
    }

    public List<SigningCertificate> getV2Certificates() {
        return signatureV2Repository.getCertificates();
    }

    public boolean isV2Signed() {
        return signatureV2Repository.isSigned();
    }

    public boolean isV2SignatureValid() {
        return signatureV2Repository.isSignatureValid();
    }

    public void close() {
        zipContents.close();
        signatureV2Repository.close();
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
    public DexClass getClassByType(DexClassType classType) {
        return dexRepository.getClassByType(classType);
    }

    @Nullable
    public String getSmaliForClassType(final DexClassType classType) {
        return smaliRepository.getSmaliForType(classType);
    }
}
