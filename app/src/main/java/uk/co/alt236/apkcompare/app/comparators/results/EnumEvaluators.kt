package uk.co.alt236.apkcompare.app.comparators.results

import uk.co.alt236.apkcompare.app.comparators.results.comparisons.Comparison

internal object EnumEvaluators {

    @JvmStatic
    fun evaluateSimilarity(items: Collection<ComparisonResult>): Similarity {

        for (result in items) {
            if (result.similarity !== Similarity.IDENTICAL) {
                return result.similarity
            }
        }

        return Similarity.IDENTICAL
    }

    fun evaluateMissingValue(items: List<ComparisonResult>): MissingValue {
        val statusSet = HashSet<MissingValue>()

        for (item in items) {
            statusSet.add(item.missingValue)
        }

        if (statusSet.size > 1) {
            return MissingValue.MULTIPLE
        }

        return statusSet.first()
    }

    fun evaluateMissingValue(item: Comparison): MissingValue {
        if (item.value1AsString.isNull() && item.value2AsString.isNull()) {
            return MissingValue.BOTH_MISSING
        }
        if (item.value1AsString.isNull()) {
            return MissingValue.VALUE1
        }

        if (item.value2AsString.isNull()) {
            return MissingValue.VALUE2
        }

        return MissingValue.BOTH_PRESENT
    }

    private fun Any?.isNull(): Boolean {
        return this == null
    }
}
