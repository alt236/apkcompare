package uk.co.alt236.apkcompare.apk;

import uk.co.alt236.apkcompare.repo.dex.DexRepository;
import uk.co.alt236.apkcompare.repo.signature.SignatureRepository;
import uk.co.alt236.apkcompare.repo.smali.SmaliRepository;
import uk.co.alt236.apkcompare.zip.common.ZipContents;

import java.io.File;

public final class ApkFactory {

    public static Apk from(final File file) {
        final ZipContents zipContents = new ZipContents(file);
        final SignatureRepository signatureRepository = new SignatureRepository(file);
        final DexRepository dexRepository = new DexRepository(zipContents);
        final SmaliRepository smaliRepository = new SmaliRepository(file, dexRepository);


        return new Apk(file, zipContents, signatureRepository, dexRepository, smaliRepository);
    }
}
