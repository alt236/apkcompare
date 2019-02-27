package uk.co.alt236.apkcompare.app.comparators.results.comparisons;

import uk.co.alt236.apkcompare.app.comparators.results.CollectionSimilarityEvaluator;
import uk.co.alt236.apkcompare.app.comparators.results.ComparisonResult;
import uk.co.alt236.apkcompare.app.comparators.results.Similarity;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class CompositeResult implements ComparisonResult {
    private final List<Comparison> comparisons;
    private final String title;

    private CompositeResult(Builder builder) {
        comparisons = builder.comparisons;
        title = builder.title;
    }

    @Override
    public Similarity getSimilarity() {
        return CollectionSimilarityEvaluator.evaluate(comparisons);
    }

    @Override
    public String getTitle() {
        return title;
    }

    public List<Comparison> getComparisons() {
        return comparisons;
    }

    public static final class Builder {
        private List<Comparison> comparisons = new ArrayList<>();
        private String title;

        public Builder() {
        }

        @Nonnull
        public Builder withComparison(@Nonnull Comparison val) {
            comparisons.add(val);
            return this;
        }

        @Nonnull
        public Builder withTitle(@Nonnull String val) {
            title = val;
            return this;
        }

        @Nonnull
        public CompositeResult build() {
            return new CompositeResult(this);
        }
    }
}
