package uk.co.alt236.apkcompare.app.comparators.results.groups

import uk.co.alt236.apkcompare.app.comparators.results.ComparisonResult
import uk.co.alt236.apkcompare.app.comparators.results.EnumEvaluators
import uk.co.alt236.apkcompare.app.comparators.results.MissingValue
import uk.co.alt236.apkcompare.app.comparators.results.Similarity
import uk.co.alt236.apkcompare.app.comparators.results.comparisons.Comparison
import java.util.*

class CompositeResult private constructor(builder: Builder) : ComparisonResult {
    override val title: String = builder.title
    override val similarity: Similarity by lazy { EnumEvaluators.evaluateSimilarity(comparisons) }
    override val missingValue: MissingValue by lazy { EnumEvaluators.evaluateMissingValue(comparisons) }
    val comparisons = builder.comparisons

    class Builder {
        val comparisons = ArrayList<Comparison>()
        var title: String = ""

        fun withComparisons(value: List<Comparison>) {
            comparisons.addAll(value)
        }

        fun withComparison(value: Comparison): Builder {
            comparisons.add(value)
            return this
        }

        fun withTitle(value: String): Builder {
            title = value
            return this
        }

        fun build(): CompositeResult {
            return CompositeResult(this)
        }
    }
}
