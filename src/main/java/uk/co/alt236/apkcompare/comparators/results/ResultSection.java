package uk.co.alt236.apkcompare.comparators.results;

import java.util.List;

public class ResultSection implements ComparisonResult {

    private final List<ResultBlock> resultBlocks;
    private final String title;

    public ResultSection(String title,
                         List<ResultBlock> resultBlocks) {
        this.title = title;
        this.resultBlocks = resultBlocks;
    }

    public List<ResultBlock> getResultBlocks() {
        return resultBlocks;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Similarity getSimilarity() {
        return CollectionSimilarityEvaluator.evaluate(resultBlocks);
    }
}
