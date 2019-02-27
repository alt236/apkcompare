package uk.co.alt236.apkcompare.app.output;

import uk.co.alt236.apkcompare.app.comparators.results.ComparisonResult;
import uk.co.alt236.apkcompare.app.comparators.results.Similarity;

public class PrintabilityEvaluator {

    public boolean isPrintable(final ComparisonResult item,
                               final boolean verbose) {
        if (verbose) {
            return true;
        }

        return item.getSimilarity() != Similarity.IDENTICAL;
    }
}
