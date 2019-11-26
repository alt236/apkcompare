package uk.co.alt236.apkcompare.app.comparators.dex;

import org.jetbrains.annotations.NotNull;
import uk.co.alt236.apk.Apk;
import uk.co.alt236.apk.repo.dex.model.DexClass;
import uk.co.alt236.apk.repo.dex.model.DexClassType;
import uk.co.alt236.apk.util.Hasher;
import uk.co.alt236.apkcompare.app.comparators.ApkComparator;
import uk.co.alt236.apkcompare.app.comparators.results.ComparisonResult;
import uk.co.alt236.apkcompare.app.comparators.results.groups.ResultBlock;

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
        final DexClassComparator classComparator = new DexClassComparator(new Hasher(), apk1, apk2);
        final DexMethodComparator methodComparator = new DexMethodComparator(apk1, apk2);


        final List<ResultBlock> retVal = new ArrayList<>();
        final List<ComparisonResult> allComparisons = new ArrayList<>();

        for (final DexClassType classType : classTypeList) {
            final List<ComparisonResult> inClassComparisons = new ArrayList<>();

            final ComparisonResult overview = classComparator.compareClasses(classType);
            final List<ComparisonResult> methods = methodComparator.compareClasses(classType);

            inClassComparisons.add(overview);
            inClassComparisons.addAll(methods);

            allComparisons.add(new ResultBlock(classType.getType(), inClassComparisons, false));
        }

        retVal.add(new ResultBlock("Class Comparison", allComparisons, true));
        return retVal;
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

    @NotNull
    @Override
    public String getName() {
        return "Dex Comparator";
    }
}
