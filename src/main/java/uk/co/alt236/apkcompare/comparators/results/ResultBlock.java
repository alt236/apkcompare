package uk.co.alt236.apkcompare.comparators.results;

import java.util.List;

public class ResultBlock implements ComparisonResult {

    private final List<ResultItem> resultItems;
    private final String title;

    public ResultBlock(String title,
                       List<ResultItem> resultItems) {
        this.title = title;
        this.resultItems = resultItems;
    }

    public List<ResultItem> getResultItems() {
        return resultItems;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Similarity getSimilarity() {
        return CollectionSimilarityEvaluator.evaluate(resultItems);
    }

}
