package uk.co.alt236.apkcompare.comparators.file;

import uk.co.alt236.apkcompare.apk.Apk;
import uk.co.alt236.apkcompare.comparators.ApkComparator;
import uk.co.alt236.apkcompare.comparators.results.*;
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
    public List<ResultSection> compare(Apk file1, Apk file2) {
        final List<ResultSection> retVal = new ArrayList<>();

        final List<ResultBlock> signatureComparison = compareEntryLists(file1, file2);
        retVal.add(new ResultSection("Contents Comparison", signatureComparison));

        return retVal;
    }

    private List<ResultBlock> compareEntryLists(Apk file1, Apk file2) {
        final List<ResultBlock> retVal = new ArrayList<>();
        final List<ResultItem> resultItems = new ArrayList<>();

        final List<String> nameList = getKeyList(file1.getContents(), file2.getContents());

        for (final String name : nameList) {


            final ResultItem resultItem = compare(name, file1, file2);
            resultItems.add(resultItem);
        }

        retVal.add(new ResultBlock("File Contents", resultItems));
        return retVal;
    }

    private ResultItem compare(@Nonnull String name,
                               @Nonnull Apk apk1,
                               @Nonnull Apk apk2) {

        final Entry entry1 = apk1.getEntry(name);
        final Entry entry2 = apk2.getEntry(name);

        final long fileSize1 = entry1 == null ? -1 : entry1.getFileSize();
        final long fileSize2 = entry2 == null ? -1 : entry2.getFileSize();

        final ResultItem resultItem;
        if (fileSize1 != fileSize2) {
            resultItem = new ByteCountResultItem(
                    name,
                    "File size",
                    entry1 == null ? null : fileSize1,
                    entry2 == null ? null : fileSize2);
        } else {
            final String hash1 = entry1 == null ? null : hasher.sha256Hex(apk1.getEntryStream(entry1));
            final String hash2 = entry2 == null ? null : hasher.sha256Hex(apk2.getEntryStream(entry2));

            resultItem = new StringResultItem(name, "SHA256", hash1, hash2);
        }


        return resultItem;
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
