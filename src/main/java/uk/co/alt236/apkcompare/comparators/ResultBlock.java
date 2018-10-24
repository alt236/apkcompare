package uk.co.alt236.apkcompare.comparators;


import java.util.List;

public class ResultBlock implements ComparisonResult {

    private final List<ResultItem> resultItems;
    private final String title;

    public ResultBlock(String title, List<ResultItem> resultItems) {
        this.resultItems = resultItems;
        this.title = title;
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
