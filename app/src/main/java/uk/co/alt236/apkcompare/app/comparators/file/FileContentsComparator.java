package uk.co.alt236.apkcompare.app.comparators.file;

import org.jetbrains.annotations.NotNull;
import uk.co.alt236.apk.Apk;
import uk.co.alt236.apk.util.Hasher;
import uk.co.alt236.apk.zip.Entry;
import uk.co.alt236.apkcompare.app.comparators.ApkComparator;
import uk.co.alt236.apkcompare.app.comparators.results.ComparisonResult;
import uk.co.alt236.apkcompare.app.comparators.results.comparisons.ByteCountComparison;
import uk.co.alt236.apkcompare.app.comparators.results.comparisons.TypedComparison;
import uk.co.alt236.apkcompare.app.comparators.results.groups.CompositeResult;
import uk.co.alt236.apkcompare.app.comparators.results.groups.ResultBlock;

import javax.annotation.Nonnull;
import java.util.*;

public class FileContentsComparator implements ApkComparator {

    private final Hasher hasher;

    public FileContentsComparator() {
        hasher = new Hasher();
    }

    @NotNull
    @Override
    public List<ComparisonResult> compare(@Nonnull Apk file1, @Nonnull Apk file2) {
        final List<ComparisonResult> retVal = new ArrayList<>();

        final List<ComparisonResult> signatureComparison = compareEntryLists(file1, file2);
        retVal.add(new ResultBlock("Contents Comparison", signatureComparison));

        return retVal;
    }

    private List<ComparisonResult> compareEntryLists(Apk file1, Apk file2) {
        final List<ComparisonResult> retVal = new ArrayList<>();
        final List<ComparisonResult> comparisons = new ArrayList<>();

        final List<String> nameList = getKeyList(file1.getContents(), file2.getContents());

        for (final String name : nameList) {
            final ComparisonResult comparison = compare(name, file1, file2);
            comparisons.add(comparison);
        }

        retVal.add(new ResultBlock("File Contents", comparisons));
        return retVal;
    }

    private CompositeResult compare(@Nonnull String name,
                                    @Nonnull Apk apk1,
                                    @Nonnull Apk apk2) {

        final Entry entry1 = apk1.getEntry(name);
        final Entry entry2 = apk2.getEntry(name);

        final long fileSize1 = entry1 == null ? -1 : entry1.getFileSize();
        final long fileSize2 = entry2 == null ? -1 : entry2.getFileSize();

        final String hash1 = entry1 == null ? null : hasher.sha256Hex(apk1.getEntryStream(entry1));
        final String hash2 = entry2 == null ? null : hasher.sha256Hex(apk2.getEntryStream(entry2));

        return new CompositeResult
                .Builder()
                .withTitle(name)
                .withComparison(new ByteCountComparison(name, "File size", entry1 == null ? null : fileSize1, entry2 == null ? null : fileSize2))
                .withComparison(new TypedComparison<>(name, "SHA256", hash1, hash2))
                .build();
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

    @NotNull
    @Override
    public String getName() {
        return "File Contents Comparator";
    }
}
