package uk.co.alt236.apkcompare.app.comparators.results

data class ResultBlock @JvmOverloads constructor(override val title: String,
                                                 val comparisonResults: List<ComparisonResult>,
                                                 val complex: Boolean = false) : ComparisonResult {

    override val similarity: Similarity by lazy {
        CollectionSimilarityEvaluator.evaluate(comparisonResults)
    }
}
