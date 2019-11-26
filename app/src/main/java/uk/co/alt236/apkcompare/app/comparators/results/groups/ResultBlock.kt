package uk.co.alt236.apkcompare.app.comparators.results.groups

import uk.co.alt236.apkcompare.app.comparators.results.ComparisonResult
import uk.co.alt236.apkcompare.app.comparators.results.EnumEvaluators
import uk.co.alt236.apkcompare.app.comparators.results.MissingValue
import uk.co.alt236.apkcompare.app.comparators.results.Similarity

data class ResultBlock @JvmOverloads constructor(override val title: String,
                                                 val comparisonResults: List<ComparisonResult>,
                                                 val complex: Boolean = false) : ComparisonResult {

    override val similarity: Similarity by lazy { EnumEvaluators.evaluateSimilarity(comparisonResults) }
    override val missingValue: MissingValue by lazy { EnumEvaluators.evaluateMissingValue(comparisonResults) }
}
