package uk.co.alt236.apkcompare.app.comparators;

import uk.co.alt236.apk.Apk;
import uk.co.alt236.apkcompare.app.comparators.results.ComparisonResult;

import java.util.List;


public interface ApkComparator {
    List<ComparisonResult> compare(Apk file1, Apk file2);
}