package uk.co.alt236.apkcompare.comparators.dex;

import uk.co.alt236.apkcompare.apk.Apk;
import uk.co.alt236.apkcompare.comparators.results.ResultItem;
import uk.co.alt236.apkcompare.comparators.results.StringResultItem;
import uk.co.alt236.apkcompare.util.Hasher;

class SmaliComparator {

    private final Apk apk1;
    private final Apk apk2;

    private final Hasher hasher;

    public SmaliComparator(final Hasher hasher,
                           final Apk apk1,
                           final Apk apk2) {
        this.apk1 = apk1;
        this.apk2 = apk2;

        this.hasher = hasher;
    }

    public ResultItem compareClasses(final String classType) {

        final String apk1ClassContents = apk1.getSmaliForClassType(classType);
        final String apk2ClassContents = apk2.getSmaliForClassType(classType);


        return new StringResultItem(
                classType,
                "SHA256",
                apk1ClassContents == null ? null : hasher.sha256Hex(apk1ClassContents.getBytes()),
                apk2ClassContents == null ? null : hasher.sha256Hex(apk2ClassContents.getBytes()));
    }
}
