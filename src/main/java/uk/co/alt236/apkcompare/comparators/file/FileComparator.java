package uk.co.alt236.apkcompare.comparators.file;

import uk.co.alt236.apkcompare.apk.Apk;
import uk.co.alt236.apkcompare.comparators.ApkComparator;
import uk.co.alt236.apkcompare.comparators.results.ComparisonResult;
import uk.co.alt236.apkcompare.comparators.results.ResultBlock;
import uk.co.alt236.apkcompare.comparators.results.comparisons.Comparison;
import uk.co.alt236.apkcompare.comparators.results.comparisons.TypedComparison;
import uk.co.alt236.apkcompare.util.Hasher;

import java.util.ArrayList;
import java.util.List;

public class FileComparator implements ApkComparator {

    private final Hasher hasher;

    public FileComparator() {
        hasher = new Hasher();
    }

    @Override
    public List<ComparisonResult> compare(Apk file1, Apk file2) {
        final List<ComparisonResult> retVal = new ArrayList<>();

        final List<ComparisonResult> signatureComparison = compareFiles(file1, file2);
        retVal.add(new ResultBlock("File Comparison", signatureComparison));

        return retVal;
    }

    private List<ComparisonResult> compareFiles(Apk apk1, Apk apk2) {
        final List<ComparisonResult> retVal = new ArrayList<>();
        final List<Comparison> comparisons = new ArrayList<>();

        comparisons.add(new TypedComparison<>("MD5", null, hasher.md5Hex(apk1.getFile()), hasher.md5Hex(apk2.getFile())));
        comparisons.add(new TypedComparison<>("SHA1", null, hasher.sha1Hex(apk1.getFile()), hasher.sha1Hex(apk2.getFile())));
        comparisons.add(new TypedComparison<>("SHA256", null, hasher.sha256Hex(apk1.getFile()), hasher.sha256Hex(apk2.getFile())));


        retVal.add(new ResultBlock("File Hashes", comparisons));
        return retVal;
    }

}
