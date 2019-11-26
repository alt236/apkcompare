package uk.co.alt236.apkcompare.app.comparators.results.comparisons

import uk.co.alt236.apk.repo.dex.DexClassTypeToString
import uk.co.alt236.apk.repo.dex.model.DexClassType
import uk.co.alt236.apkcompare.app.comparators.results.EnumEvaluators
import uk.co.alt236.apkcompare.app.comparators.results.MissingValue
import uk.co.alt236.apkcompare.app.comparators.results.Similarity

class ClassTypeComparison(override val title: String,
                          override val comparedAttribute: String?,
                          val value1: DexClassType?,
                          val value2: DexClassType?) : Comparison {

    override val missingValue: MissingValue by lazy { EnumEvaluators.evaluateMissingValue(this) }

    override val value1AsString = if (value1 == null) null else toFriendlyString(value1)

    override val value2AsString = if (value2 == null) null else toFriendlyString(value2)

    override val similarity: Similarity
        get() = if (value1 == value2) {
            Similarity.IDENTICAL
        } else {
            Similarity.DIFFERENT
        }

    private fun toFriendlyString(value: DexClassType): String {
        return DexClassTypeToString.toString(value)
    }

}