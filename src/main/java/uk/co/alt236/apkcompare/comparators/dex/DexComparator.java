package uk.co.alt236.apkcompare.comparators.dex;

import uk.co.alt236.apkcompare.apk.Apk;
import uk.co.alt236.apkcompare.comparators.ApkComparator;
import uk.co.alt236.apkcompare.comparators.results.ByteCountResultItem;
import uk.co.alt236.apkcompare.comparators.results.ResultBlock;
import uk.co.alt236.apkcompare.comparators.results.ResultItem;
import uk.co.alt236.apkcompare.comparators.results.ResultSection;
import uk.co.alt236.apkcompare.repo.dex.model.DexClass;
import uk.co.alt236.apkcompare.repo.dex.model.DexClassType;
import uk.co.alt236.apkcompare.util.Hasher;

import java.util.*;

public class DexComparator implements ApkComparator {

    @Override
    public List<ResultSection> compare(Apk file1, Apk file2) {
        final List<ResultSection> retVal = new ArrayList<>();

        final List<ResultBlock> classComparison = compareClasses(file1, file2);
        retVal.add(new ResultSection("Dex Comparison", classComparison));

        return retVal;
    }

    private List<ResultBlock> compareClasses(Apk apk1, Apk apk2) {
        final SmaliComparator smaliComparator = new SmaliComparator(new Hasher(), apk1, apk2);
        final List<ResultBlock> retVal = new ArrayList<>();
        final List<ResultItem> resultItems = new ArrayList<>();
        final List<DexClassType> classTypeList = getClassTypeList(apk1.getClasses(), apk2.getClasses());

        for (final DexClassType classType : classTypeList) {

            final ResultItem resultItem = compare(classType, apk1, apk2, smaliComparator);
            resultItems.add(resultItem);
        }

        retVal.add(new ResultBlock("Class Comparison", resultItems));
        return retVal;
    }

    private ResultItem compare(DexClassType classType,
                               Apk apk1,
                               Apk apk2,
                               SmaliComparator smaliComparator) {

        final DexClass class1 = apk1.getClassByType(classType);
        final DexClass class2 = apk2.getClassByType(classType);

        final long fileSize1 = class1 == null ? -1 : class1.getSize();
        final long fileSize2 = class2 == null ? -1 : class2.getSize();

        final ResultItem resultItem;
        if (fileSize1 != fileSize2) {
            resultItem = new ByteCountResultItem(
                    classType.getType(),
                    "Class size",
                    class1 == null ? null : fileSize1,
                    class2 == null ? null : fileSize2);
        } else {
            return smaliComparator.compareClasses(classType);
        }


        return resultItem;
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
