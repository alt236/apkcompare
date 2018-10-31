package uk.co.alt236.apkcompare.comparators.file;

import uk.co.alt236.apkcompare.apk.Apk;
import uk.co.alt236.apkcompare.comparators.ApkComparator;
import uk.co.alt236.apkcompare.comparators.results.ComparisonResult;
import uk.co.alt236.apkcompare.comparators.results.ResultBlock;
import uk.co.alt236.apkcompare.comparators.results.comparisons.ByteCountComparison;
import uk.co.alt236.apkcompare.comparators.results.comparisons.Comparison;
import uk.co.alt236.apkcompare.comparators.results.comparisons.TypedComparison;
import uk.co.alt236.apkcompare.util.Hasher;
import uk.co.alt236.apkcompare.zip.common.Entry;

import javax.annotation.Nonnull;
import java.util.*;

public class FileContentsComparator implements ApkComparator {

    private final Hasher hasher;

    public FileContentsComparator() {
        hasher = new Hasher();
    }

    @Override
    public List<ComparisonResult> compare(Apk file1, Apk file2) {
        final List<ComparisonResult> retVal = new ArrayList<>();

        final List<ComparisonResult> signatureComparison = compareEntryLists(file1, file2);
        retVal.add(new ResultBlock("Contents Comparison", signatureComparison));

        return retVal;
    }

    private List<ComparisonResult> compareEntryLists(Apk file1, Apk file2) {
        final List<ComparisonResult> retVal = new ArrayList<>();
        final List<Comparison> comparisons = new ArrayList<>();

        final List<String> nameList = getKeyList(file1.getContents(), file2.getContents());

        for (final String name : nameList) {
            final Comparison comparison = compare(name, file1, file2);
            comparisons.add(comparison);
        }

        retVal.add(new ResultBlock("File Contents", comparisons));
        return retVal;
    }

    private Comparison compare(@Nonnull String name,
                               @Nonnull Apk apk1,
                               @Nonnull Apk apk2) {

        final Entry entry1 = apk1.getEntry(name);
        final Entry entry2 = apk2.getEntry(name);

        final long fileSize1 = entry1 == null ? -1 : entry1.getFileSize();
        final long fileSize2 = entry2 == null ? -1 : entry2.getFileSize();

        final Comparison comparison;
        if (fileSize1 != fileSize2) {
            comparison = new ByteCountComparison(
                    name,
                    "File size",
                    entry1 == null ? null : fileSize1,
                    entry2 == null ? null : fileSize2);
        } else {
            final String hash1 = entry1 == null ? null : hasher.sha256Hex(apk1.getEntryStream(entry1));
            final String hash2 = entry2 == null ? null : hasher.sha256Hex(apk2.getEntryStream(entry2));

            comparison = new TypedComparison<>(name, "SHA256", hash1, hash2);
        }


        return comparison;
    }


    private List<String> getKeyList(List<Entry> apk1Entries,
                                    List<Entry> apk2Entries) {
        final Set<String> keySet = new HashSet<>();

        apk1Entries
                .stream()
                .map(Entry::getName)
                .forEach(keySet::add);

        apk2Entries
                .stream()
                .map(Entry::getName)
                .forEach(keySet::add);

        final List<String> retVal = new ArrayList<>(keySet);
        Collections.sort(retVal);

        return retVal;
    }
}
