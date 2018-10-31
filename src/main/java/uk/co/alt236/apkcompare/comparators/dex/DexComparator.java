package uk.co.alt236.apkcompare.comparators.dex;

import uk.co.alt236.apkcompare.apk.Apk;
import uk.co.alt236.apkcompare.comparators.ApkComparator;
import uk.co.alt236.apkcompare.comparators.results.ComparisonResult;
import uk.co.alt236.apkcompare.comparators.results.ResultBlock;
import uk.co.alt236.apkcompare.comparators.results.comparisons.ByteCountComparison;
import uk.co.alt236.apkcompare.comparators.results.comparisons.CompositeResult;
import uk.co.alt236.apkcompare.repo.dex.model.DexClass;
import uk.co.alt236.apkcompare.repo.dex.model.DexClassType;
import uk.co.alt236.apkcompare.util.Hasher;

import java.util.*;

public class DexComparator implements ApkComparator {

    @Override
    public List<ComparisonResult> compare(Apk file1, Apk file2) {
        final List<ComparisonResult> retVal = new ArrayList<>();

        final List<ResultBlock> classComparison = compareClasses(file1, file2);
        retVal.add(new ResultBlock("Dex Comparison", classComparison));

        return retVal;
    }

    private List<ResultBlock> compareClasses(Apk apk1, Apk apk2) {
        final SmaliComparator smaliComparator = new SmaliComparator(new Hasher(), apk1, apk2);
        final List<ResultBlock> retVal = new ArrayList<>();
        final List<ComparisonResult> comparisons = new ArrayList<>();
        final List<DexClassType> classTypeList = getClassTypeList(apk1.getClasses(), apk2.getClasses());

        for (final DexClassType classType : classTypeList) {

            final CompositeResult result = compare(classType, apk1, apk2, smaliComparator);
            comparisons.add(result);
        }

        retVal.add(new ResultBlock("Class Comparison", comparisons));
        return retVal;
    }

    private CompositeResult compare(DexClassType classType,
                                    Apk apk1,
                                    Apk apk2,
                                    SmaliComparator smaliComparator) {

        final DexClass class1 = apk1.getClassByType(classType);
        final DexClass class2 = apk2.getClassByType(classType);

        final long fileSize1 = class1 == null ? -1 : class1.getSize();
        final long fileSize2 = class2 == null ? -1 : class2.getSize();

        final CompositeResult.Builder builder = new CompositeResult.Builder();

        builder.withTitle(classType.getType());
        builder.withComparison(new ByteCountComparison(
                classType.getType(),
                "Class size",
                class1 == null ? null : fileSize1,
                class2 == null ? null : fileSize2));

        builder.withComparison(smaliComparator.compareClasses(classType));

        return builder.build();
    }


    private List<DexClassType> getClassTypeList(Set<DexClass> apk1Classes,
                                                Set<DexClass> apk2Classes) {
        final Set<DexClassType> keySet = new HashSet<>();

        apk1Classes
                .stream()
                .map(DexClass::getType)
                .forEach(keySet::add);

        apk2Classes
                .stream()
                .map(DexClass::getType)
                .forEach(keySet::add);

        final List<DexClassType> retVal = new ArrayList<>(keySet);
        retVal.sort(Comparator.comparing(DexClassType::getType));

        return retVal;
    }
}
