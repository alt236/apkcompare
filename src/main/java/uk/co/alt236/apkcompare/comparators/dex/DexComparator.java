package uk.co.alt236.apkcompare.comparators.dex;

import uk.co.alt236.apkcompare.apk.Apk;
import uk.co.alt236.apkcompare.comparators.*;
import uk.co.alt236.apkcompare.repo.dex.model.DexClass;

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
        final List<ResultBlock> retVal = new ArrayList<>();
        final List<ResultItem> resultItems = new ArrayList<>();

        final List<String> classTypeList = getClassTypeList(apk1.getClasses(), apk2.getClasses());

        for (final String classType : classTypeList) {

            final ResultItem resultItem = compare(classType, apk1, apk2);
            resultItems.add(resultItem);
        }

        retVal.add(new ResultBlock("Class Comparison", resultItems));
        return retVal;
    }

    private ResultItem compare(String classType,
                               Apk apk1,
                               Apk apk2) {

        final DexClass class1 = apk1.getClassByType(classType);
        final DexClass class2 = apk2.getClassByType(classType);

        final long fileSize1 = class1 == null ? -1 : class1.getSize();
        final long fileSize2 = class2 == null ? -1 : class2.getSize();

        final ResultItem resultItem;
        if (fileSize1 != fileSize2) {
            resultItem = new CustomResultItem(
                    classType,
                    class1 == null ? null : "Class size: " + fileSize1,
                    class2 == null ? null : "Class size: " + fileSize2,
                    Similarity.DIFFERENT);
        } else {
            return new StringResultItem(classType, "[TEST]", "[TEST]");
//            final String hash1 = class1 == null ? null : hasher.sha256Hex(apk1.getEntryStream(class1));
//            final String hash2 = class2 == null ? null : hasher.sha256Hex(apk2.getEntryStream(class2));
//
//            resultItem = new StringResultItem(name, hash1, hash2);
        }


        return resultItem;
    }


    private List<String> getClassTypeList(Set<DexClass> apk1Classes,
                                          Set<DexClass> apk2Classes) {
        final Set<String> keySet = new HashSet<>();

        apk1Classes
                .stream()
                .map(DexClass::getType)
                .forEach(keySet::add);

        apk2Classes
                .stream()
                .map(DexClass::getType)
                .forEach(keySet::add);

        final List<String> retVal = new ArrayList<>(keySet);
        Collections.sort(retVal);

        return retVal;
    }
}
