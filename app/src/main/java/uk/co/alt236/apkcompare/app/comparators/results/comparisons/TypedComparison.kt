package uk.co.alt236.apkcompare.app.comparators.results.comparisons

import org.apache.commons.codec.binary.StringUtils
import uk.co.alt236.apkcompare.app.comparators.results.EnumEvaluators
import uk.co.alt236.apkcompare.app.comparators.results.MissingValue
import uk.co.alt236.apkcompare.app.comparators.results.Similarity

class TypedComparison<T>(override val title: String,
                         override val comparedAttribute: String?,
                         val value1: T?,
                         val value2: T?) : Comparison {

    override val missingValue: MissingValue by lazy { EnumEvaluators.evaluateMissingValue(this) }

    override val value1AsString = value1?.toString()

    override val value2AsString = value2?.toString()

    override val similarity: Similarity
        get() = if (value1 is String && value2 is String) {
            if (StringUtils.equals(value1 as String?, value2 as String?)) {
                Similarity.IDENTICAL
            } else {
                Similarity.DIFFERENT
            }
        } else {
            if (value1 == value2) {
                Similarity.IDENTICAL
            } else {
                Similarity.DIFFERENT
            }
        }
}
