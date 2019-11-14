package uk.co.alt236.apkcompare.app.comparators.results.comparisons

import uk.co.alt236.apkcompare.app.comparators.results.CollectionSimilarityEvaluator
import uk.co.alt236.apkcompare.app.comparators.results.ComparisonResult
import uk.co.alt236.apkcompare.app.comparators.results.Similarity
import java.util.*

class CompositeResult private constructor(builder: Builder) : ComparisonResult {
    val comparisons = builder.comparisons
    override val title: String = builder.title

    override val similarity: Similarity by lazy { CollectionSimilarityEvaluator.evaluate(comparisons) }


    class Builder {
        val comparisons = ArrayList<Comparison>()
        var title: String = ""

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
