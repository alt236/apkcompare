package uk.co.alt236.apkcompare.app.comparators.results;

import java.util.Collection;

public class CollectionSimilarityEvaluator {

    public static Similarity evaluate(final Collection<? extends ComparisonResult> items) {

        for (final ComparisonResult result : items) {
            if (result.getSimilarity() != Similarity.IDENTICAL) {
                return result.getSimilarity();
            }
        }

        return Similarity.IDENTICAL;
    }
}
