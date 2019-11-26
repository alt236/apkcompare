package uk.co.alt236.apkcompare.app.comparators.results.comparisons


import uk.co.alt236.apkcompare.app.comparators.results.EnumEvaluators
import uk.co.alt236.apkcompare.app.comparators.results.MissingValue
import uk.co.alt236.apkcompare.app.comparators.results.Similarity

class ByteCountComparison(override val title: String,
                          override val comparedAttribute: String?,
                          val value1: Long?,
                          val value2: Long?) : Comparison {

    override val missingValue: MissingValue by lazy { EnumEvaluators.evaluateMissingValue(this) }

    override val value1AsString = value1?.toString()

    override val value2AsString = value2?.toString()

    override val similarity: Similarity
        get() = if (equals(value1, value2)) {
            Similarity.IDENTICAL
        } else {
            Similarity.DIFFERENT
        }

    private fun equals(val1: Long?, val2: Long?): Boolean {
        return if (val1 === val2) {
            true
        } else if (val1 != null && val2 != null) {
            val1 == val2
        } else {
            false
        }
    }
}