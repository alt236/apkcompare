package uk.co.alt236.apkcompare.comparators.file;

import uk.co.alt236.apkcompare.apk.Apk;
import uk.co.alt236.apkcompare.comparators.ApkComparator;
import uk.co.alt236.apkcompare.comparators.results.ResultBlock;
import uk.co.alt236.apkcompare.comparators.results.ResultItem;
import uk.co.alt236.apkcompare.comparators.results.ResultSection;
import uk.co.alt236.apkcompare.comparators.results.StringResultItem;
import uk.co.alt236.apkcompare.util.Hasher;

import java.util.ArrayList;
import java.util.List;

public class FileComparator implements ApkComparator {

    private final Hasher hasher;

    public FileComparator() {
        hasher = new Hasher();
    }

    @Override
    public List<ResultSection> compare(Apk file1, Apk file2) {
        final List<ResultSection> retVal = new ArrayList<>();

        final List<ResultBlock> signatureComparison = compareFiles(file1, file2);
        retVal.add(new ResultSection("File Comparison", signatureComparison));

        return retVal;
    }

    private List<ResultBlock> compareFiles(Apk apk1, Apk apk2) {
        final List<ResultBlock> retVal = new ArrayList<>();
        final List<ResultItem> resultItems = new ArrayList<>();

        resultItems.add(new StringResultItem("MD5", null, hasher.md5Hex(apk1.getFile()), hasher.md5Hex(apk2.getFile())));
        resultItems.add(new StringResultItem("SHA1", null, hasher.sha1Hex(apk1.getFile()), hasher.sha1Hex(apk2.getFile())));
        resultItems.add(new StringResultItem("SHA256", null, hasher.sha256Hex(apk1.getFile()), hasher.sha256Hex(apk2.getFile())));


        retVal.add(new ResultBlock("File Hashes", resultItems));
        return retVal;
    }

}
