package uk.co.alt236.apkcompare.app.comparators.results

object CollectionSimilarityEvaluator {

    @JvmStatic
    fun evaluate(items: Collection<ComparisonResult>): Similarity {

        for (result in items) {
            if (result.similarity !== Similarity.IDENTICAL) {
                return result.similarity
            }
        }

        return Similarity.IDENTICAL
    }
}
