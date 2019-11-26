package uk.co.alt236.apkcompare.app.comparators.results.comparisons

import uk.co.alt236.apkcompare.app.comparators.results.EnumEvaluators
import uk.co.alt236.apkcompare.app.comparators.results.MissingValue
import uk.co.alt236.apkcompare.app.comparators.results.Similarity
import uk.co.alt236.apkcompare.app.util.date.IsoISO8601DateParser
import java.util.*

class DateComparison(override val title: String,
                     override val comparedAttribute: String?,
                     val value1: Date?,
                     val value2: Date?) : Comparison {

    override val missingValue: MissingValue by lazy { EnumEvaluators.evaluateMissingValue(this) }

    override val value1AsString = if (value1 == null) null else toIsoDate(value1)

    override val value2AsString = if (value2 == null) null else toIsoDate(value2)

    override val similarity: Similarity
        get() = if (value1 == value2) {
            Similarity.IDENTICAL
        } else {
            Similarity.DIFFERENT
        }

    private fun toIsoDate(date: Date): String {
        return IsoISO8601DateParser.toIsoDateString(date)
    }

}