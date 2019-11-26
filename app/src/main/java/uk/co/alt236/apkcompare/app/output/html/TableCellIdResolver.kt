package uk.co.alt236.apkcompare.app.output.html

import uk.co.alt236.apkcompare.app.comparators.results.MissingValue
import uk.co.alt236.apkcompare.app.comparators.results.Similarity

internal object TableCellIdResolver {
    const val TABLE_ID_VALUE_PRESENT = "value_present"
    const val TABLE_ID_VALUE_ERROR = "value_error"
    const val TABLE_ID_VALUE_MISSING = "value_missing"
    const val TABLE_ID_VALUE_INDETERMINATE = "value_indeterminate"

    @JvmStatic
    fun getIdForSimilarity(similarity: Similarity): String? {
        return if (similarity === Similarity.IDENTICAL) null else "value_error"
    }

    @JvmStatic
    fun getStatusString(similarity: Similarity): String {
        return if (similarity === Similarity.IDENTICAL) {
            "SAME"
        } else {
            "DIFFERENT"
        }
    }

    @JvmStatic
    fun getMissingValueIdForApk1(missingValue: MissingValue): String {
        if (missingValue == MissingValue.BOTH_PRESENT || missingValue == MissingValue.VALUE2) {
            return TABLE_ID_VALUE_PRESENT
        }

        if (missingValue == MissingValue.MULTIPLE) {
            return TABLE_ID_VALUE_INDETERMINATE
        }

        return TABLE_ID_VALUE_MISSING
    }

    @JvmStatic
    fun getMissingValueIdForApk2(missingValue: MissingValue): String {
        if (missingValue == MissingValue.BOTH_PRESENT || missingValue == MissingValue.VALUE1) {
            return TABLE_ID_VALUE_PRESENT
        }
        if (missingValue == MissingValue.MULTIPLE) {
            return TABLE_ID_VALUE_INDETERMINATE
        }

        return TABLE_ID_VALUE_MISSING
    }

    @JvmStatic
    fun getMissingValueStringForApk1(missingValue: MissingValue): String {
        val id = getMissingValueIdForApk1(missingValue)
        return mapMissingValueIdToString(id)
    }

    @JvmStatic
    fun getMissingValueStringForApk2(missingValue: MissingValue): String {
        val id = getMissingValueIdForApk2(missingValue)
        return mapMissingValueIdToString(id)
    }

    private fun mapMissingValueIdToString(id: String): String {
        return when (id) {
            TABLE_ID_VALUE_PRESENT -> "PRESENT"
            TABLE_ID_VALUE_MISSING -> "MISSING"
            TABLE_ID_VALUE_INDETERMINATE -> "COMPLICATED"
            else -> throw IllegalArgumentException("Don't know how to map $id")
        }
    }
}
