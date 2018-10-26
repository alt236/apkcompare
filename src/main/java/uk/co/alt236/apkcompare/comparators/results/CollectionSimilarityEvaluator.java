package uk.co.alt236.apkcompare.comparators.results;

import java.util.Collection;
import java.util.EnumSet;
import java.util.stream.Collectors;

class CollectionSimilarityEvaluator {

    static Similarity evaluate(final Collection<? extends ComparisonResult> items) {
        EnumSet<Similarity> enumSet =
                items
                        .stream()
                        .map(ComparisonResult::getSimilarity)
                        .collect(Collectors.toCollection(() -> EnumSet.noneOf(Similarity.class)));

        if (enumSet.contains(Similarity.DIFFERENT)) {
            return Similarity.DIFFERENT;
        } else {
            return Similarity.IDENTICAL;
        }
    }
}
