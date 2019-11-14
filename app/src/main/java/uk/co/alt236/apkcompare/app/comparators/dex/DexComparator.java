package uk.co.alt236.apkcompare.app.comparators.dex;

import org.jetbrains.annotations.NotNull;
import uk.co.alt236.apk.Apk;
import uk.co.alt236.apk.repo.dex.AccessFlagsResolver;
import uk.co.alt236.apk.repo.dex.DexClassTypeToString;
import uk.co.alt236.apk.repo.dex.model.DexClass;
import uk.co.alt236.apk.repo.dex.model.DexClassType;
import uk.co.alt236.apk.repo.dex.model.DexMethod;
import uk.co.alt236.apk.repo.dex.model.Parameter;
import uk.co.alt236.apk.util.Hasher;
import uk.co.alt236.apkcompare.app.comparators.ApkComparator;
import uk.co.alt236.apkcompare.app.comparators.results.ComparisonResult;
import uk.co.alt236.apkcompare.app.comparators.results.ResultBlock;
import uk.co.alt236.apkcompare.app.comparators.results.comparisons.ByteCountComparison;
import uk.co.alt236.apkcompare.app.comparators.results.comparisons.CompositeResult;
import uk.co.alt236.apkcompare.app.comparators.results.comparisons.TypedComparison;

import javax.annotation.Nonnull;
import java.util.*;

public class DexComparator implements ApkComparator {

    @Nonnull
    @Override
    public List<ComparisonResult> compare(@Nonnull Apk apk1, @Nonnull Apk apk2) {
        final List<ComparisonResult> retVal = new ArrayList<>();

        final List<ResultBlock> classComparison = compareClasses(apk1, apk2);
        retVal.add(new ResultBlock("Dex Comparison", classComparison));

        return retVal;
    }

    private List<ResultBlock> compareClasses(Apk apk1, Apk apk2) {
        final List<DexClassType> classTypeList = getClassTypeList(apk1.getClasses(), apk2.getClasses());
        final SmaliComparator smaliComparator = new SmaliComparator(new Hasher(), apk1, apk2);

        final List<ComparisonResult> comparisons = new ArrayList<>();
        final List<ResultBlock> retVal = new ArrayList<>();

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
                "DEX File",
                class1 == null ? null : class1.getDexFileName(),
                class2 == null ? null : class2.getDexFileName()));

        builder.withComparison(new ByteCountComparison(
                classType.getType(),
                "Class Definition Size",
                class1 == null ? null : class1.getSize(),
                class2 == null ? null : class2.getSize()));

        builder.withComparison(new TypedComparison<>(
                classType.getType(),
                "Access Flags",
                class1 == null ? null : AccessFlagsResolver.resolve(class1),
                class2 == null ? null : AccessFlagsResolver.resolve(class2)));

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


//        if (class1 != null) {
//            System.out.println(class1.getType().toString());
//
//            class1.getMethods().forEach(method -> {
//                System.out.println("\t" + constructSignature(method));
//            });
//        }

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


    private String constructSignature(DexMethod method) {
        final StringBuilder sb = new StringBuilder();

        sb.append(AccessFlagsResolver.resolve(method));
        sb.append(" ");
        sb.append(DexClassTypeToString.toString(method.getReturnType()));
        sb.append(" ");
        sb.append(method.getName());
        sb.append("(");

        int count = 0;
        for (final Parameter parameter : method.getParameters()) {
            if (count > 0) {
                sb.append(", ");
            }
            sb.append(DexClassTypeToString.toString(parameter.getType()));
            count++;
        }
        sb.append(")");

        return sb.toString();
    }

    @NotNull
    @Override
    public String getName() {
        return "Dex Comparator";
    }
}
