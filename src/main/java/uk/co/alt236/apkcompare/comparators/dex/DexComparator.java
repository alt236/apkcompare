package uk.co.alt236.apkcompare.comparators.dex;

import com.android.dx.rop.code.AccessFlags;
import uk.co.alt236.apkcompare.apk.Apk;
import uk.co.alt236.apkcompare.comparators.ApkComparator;
import uk.co.alt236.apkcompare.comparators.results.ComparisonResult;
import uk.co.alt236.apkcompare.comparators.results.ResultBlock;
import uk.co.alt236.apkcompare.comparators.results.comparisons.ByteCountComparison;
import uk.co.alt236.apkcompare.comparators.results.comparisons.CompositeResult;
import uk.co.alt236.apkcompare.comparators.results.comparisons.TypedComparison;
import uk.co.alt236.apkcompare.repo.dex.model.DexClass;
import uk.co.alt236.apkcompare.repo.dex.model.DexClassType;
import uk.co.alt236.apkcompare.util.Hasher;

import javax.annotation.Nonnull;
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

        final CompositeResult.Builder builder = new CompositeResult.Builder();

        builder.withTitle(classType.getType());

        builder.withComparison(new TypedComparison<>(
                classType.getType(),
                "Sourcefile",
                class1 == null ? null : class1.getSourceFile(),
                class2 == null ? null : class2.getSourceFile()));

        builder.withComparison(new TypedComparison<>(
                classType.getType(),
                "Superclass",
                class1 == null ? null : class1.getSuperType(),
                class2 == null ? null : class2.getSuperType()));

        builder.withComparison(new TypedComparison<>(
                classType.getType(),
                "Access Flags",
                class1 == null ? null : getAccessFlags(class1),
                class2 == null ? null : getAccessFlags(class2)));

        builder.withComparison(new ByteCountComparison(
                classType.getType(),
                "Class size",
                class1 == null ? null : class1.getSize(),
                class2 == null ? null : class2.getSize()));

        builder.withComparison(new TypedComparison<>(
                classType.getType(),
                "Number of Annotations",
                class1 == null ? null : class1.getNumberOfAnnotations(),
                class2 == null ? null : class2.getNumberOfAnnotations()));

        builder.withComparison(new TypedComparison<>(
                classType.getType(),
                "Number of Interfaces",
                class1 == null ? null : class1.getNumberOfInterfaces(),
                class2 == null ? null : class2.getNumberOfInterfaces()));

        builder.withComparison(new TypedComparison<>(
                classType.getType(),
                "Number of Methods",
                class1 == null ? null : class1.getNumberOfMethods(),
                class2 == null ? null : class2.getNumberOfMethods()));

        builder.withComparison(new TypedComparison<>(
                classType.getType(),
                "Number of Fields",
                class1 == null ? null : class1.getNumberOfFields(),
                class2 == null ? null : class2.getNumberOfFields()));


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

    private String getAccessFlags(@Nonnull DexClass dexClass) {
        final String result;
        if (dexClass.isInnerClass()) {
            result = AccessFlags.innerClassString(dexClass.getAccessFlags());
        } else {
            result = AccessFlags.classString(dexClass.getAccessFlags());
        }

        if ("0000".equals(result)) {
            return "none";
        } else {
            return result;
        }
    }
}
