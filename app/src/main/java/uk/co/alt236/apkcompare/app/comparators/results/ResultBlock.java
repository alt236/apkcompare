package uk.co.alt236.apkcompare.app.comparators.results;

import java.util.List;

public class ResultBlock implements ComparisonResult {

    private final List<? extends ComparisonResult> comparisons;
    private final String title;

    public ResultBlock(String title,
                       List<? extends ComparisonResult> comparisons) {
        this.title = title;
        this.comparisons = comparisons;
    }

    public List<? extends ComparisonResult> getComparisonResults() {
        return comparisons;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Similarity getSimilarity() {
        return CollectionSimilarityEvaluator.evaluate(comparisons);
    }

}
