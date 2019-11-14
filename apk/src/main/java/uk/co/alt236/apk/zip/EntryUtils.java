package uk.co.alt236.apk.zip;

import uk.co.alt236.apk.util.ImmutableCollectors;

import java.util.List;

public final class EntryUtils {

    public static List<String> toListOfNames(final List<Entry> entries) {
        return entries
                .stream()
                .map(Entry::getName)
                .collect(ImmutableCollectors.toImmutableList());
    }
}
