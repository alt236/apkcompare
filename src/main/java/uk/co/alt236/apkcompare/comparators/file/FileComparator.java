package uk.co.alt236.apkcompare.comparators.file;

import uk.co.alt236.apkcompare.comparators.ApkComparator;
import uk.co.alt236.apkcompare.comparators.ResultSection;
import uk.co.alt236.apkcompare.util.Colorizer;
import uk.co.alt236.apkcompare.util.FileSizeFormatter;

import java.io.File;
import java.util.List;

public class FileComparator implements ApkComparator {
    public FileComparator(FileSizeFormatter fileSizeFormatter, Colorizer colorizer, boolean verbose) {

    }

    @Override
    public List<ResultSection> compare(File file1, File file2) {
        return null;
    }
}
