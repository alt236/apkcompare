package uk.co.alt236.apkcompare.comparators;

import java.io.File;
import java.util.List;

public interface ApkComparator {
    List<ResultSection> compare(File file1, File file2);
}