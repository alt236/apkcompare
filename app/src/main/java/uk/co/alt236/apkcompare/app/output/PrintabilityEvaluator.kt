package uk.co.alt236.apkcompare.app.output

import uk.co.alt236.apkcompare.app.comparators.results.ComparisonResult
import uk.co.alt236.apkcompare.app.comparators.results.Similarity

class PrintabilityEvaluator {

    fun isPrintable(item: ComparisonResult,
                    verbose: Boolean): Boolean {
        return if (verbose) {
            true
        } else item.similarity !== Similarity.IDENTICAL

    }
}
