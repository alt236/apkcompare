package uk.co.alt236.apkcompare.comparators.results;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CustomResultItem implements ResultItem {

    private final String title;
    private final String value1;
    private final String value2;
    private final Similarity similarity;
    private final String comparedAttribute;

    public CustomResultItem(@Nonnull String title,
                            @Nullable String comparedAttribute,
                            @Nullable String value1,
                            @Nullable String value2,
                            @Nonnull Similarity similarity) {
        this.title = title;
        this.comparedAttribute = comparedAttribute;
        this.value1 = value1;
        this.value2 = value2;
        this.similarity = similarity;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Nullable
    @Override
    public String getValue1AsString() {
        return value1;
    }

    @Nullable
    @Override
    public String getValue2AsString() {
        return value2;
    }

    @Nullable
    @Override
    public String getComparedAttribute() {
        return comparedAttribute;
    }

    @Override
    public Similarity getSimilarity() {
        return similarity;
    }
}
