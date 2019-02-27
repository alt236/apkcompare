package uk.co.alt236.apk;

import uk.co.alt236.apk.repo.dex.DexRepository;
import uk.co.alt236.apk.repo.signature.v1.SignatureV1Repository;
import uk.co.alt236.apk.repo.signature.v2.SignatureV2Repository;
import uk.co.alt236.apk.repo.smali.SmaliRepository;
import uk.co.alt236.apk.zip.ZipContents;

import java.io.File;

public final class ApkFactory {

    public static Apk from(final File file) {
        final ZipContents zipContents = new ZipContents(file);
        final SignatureV1Repository signatureV1Repository = new SignatureV1Repository(file);
        final SignatureV2Repository signatureV2Repository = new SignatureV2Repository(file);
        final DexRepository dexRepository = new DexRepository(zipContents);
        final SmaliRepository smaliRepository = new SmaliRepository(file, dexRepository);


        return new Apk(file, zipContents, signatureV1Repository, signatureV2Repository, dexRepository, smaliRepository);
    }
}
