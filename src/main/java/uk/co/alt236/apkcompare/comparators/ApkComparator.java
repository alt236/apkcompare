package uk.co.alt236.apkcompare.comparators;

import uk.co.alt236.apkcompare.apk.Apk;

import java.util.List;

public interface ApkComparator {
    List<ResultSection> compare(Apk file1, Apk file2);
}