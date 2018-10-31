package uk.co.alt236.apkcompare.comparators;

import uk.co.alt236.apkcompare.apk.Apk;
import uk.co.alt236.apkcompare.comparators.results.ComparisonResult;

import java.util.List;

public interface ApkComparator {
    List<ComparisonResult> compare(Apk file1, Apk file2);
}