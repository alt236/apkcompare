package uk.co.alt236.apkcompare.apk;

import uk.co.alt236.apkcompare.repo.signature.SignatureRepository;
import uk.co.alt236.apkcompare.zip.common.ZipContents;

import java.io.File;

public final class ApkFactory {

    public static Apk from(final File file) {
        final ZipContents zipContents = new ZipContents(file);
        final SignatureRepository signatureRepository = new SignatureRepository(file);

        return new Apk(file, zipContents, signatureRepository);
    }
}
