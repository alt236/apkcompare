package uk.co.alt236.apkcompare.output;

import uk.co.alt236.apkcompare.comparators.results.ComparisonResult;
import uk.co.alt236.apkcompare.comparators.results.Similarity;

public class PrintabilityEvaluator {

    public boolean isPrintable(final ComparisonResult item,
                               final boolean verbose) {
        if (verbose) {
            return true;
        }

        return item.getSimilarity() != Similarity.IDENTICAL;
    }
}
