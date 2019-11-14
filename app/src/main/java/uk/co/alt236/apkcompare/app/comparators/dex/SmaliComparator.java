package uk.co.alt236.apkcompare.app.comparators.dex;

import uk.co.alt236.apk.Apk;
import uk.co.alt236.apk.repo.dex.model.DexClassType;
import uk.co.alt236.apk.util.Hasher;
import uk.co.alt236.apkcompare.app.comparators.results.comparisons.Comparison;
import uk.co.alt236.apkcompare.app.comparators.results.comparisons.TypedComparison;

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

    public Comparison compareClasses(final DexClassType classType) {

        final String apk1ClassContents = apk1.getSmaliForClassType(classType);
        final String apk2ClassContents = apk2.getSmaliForClassType(classType);

        return new TypedComparison<>(
                classType.getType(),
                "Smali SHA256",
                apk1ClassContents == null ? null : hasher.sha256Hex(apk1ClassContents),
                apk2ClassContents == null ? null : hasher.sha256Hex(apk2ClassContents));
    }
}
