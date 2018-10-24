package uk.co.alt236.apkcompare.comparators;

import java.util.Collection;
import java.util.EnumSet;
import java.util.stream.Collectors;

class CollectionStatusEvaluator {

    static Status evaluate(final Collection<? extends ComparisonResult> items) {
        EnumSet<Status> enumSet =
                items
                        .stream()
                        .map(ComparisonResult::getStatus)
                        .collect(Collectors.toCollection(() -> EnumSet.noneOf(Status.class)));

        if (enumSet.contains(Status.DIFFERENT)) {
            return Status.DIFFERENT;
        } else {
            return Status.SAME;
        }
    }
}
