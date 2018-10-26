package uk.co.alt236.apkcompare.comparators.results;

import org.apache.commons.codec.binary.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StringResultItem implements ResultItem {

    private final String title;
    private final String value1;
    private final String value2;
    private final String comparedAttribute;

    public StringResultItem(@Nonnull String title,
                            @Nullable String comparedAttribute,
                            @Nullable String value1,
                            @Nullable String value2) {
        this.title = title;
        this.comparedAttribute = comparedAttribute;
        this.value1 = value1;
        this.value2 = value2;
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
        if (StringUtils.equals(value1, value2)) {
            return Similarity.IDENTICAL;
        } else {
            return Similarity.DIFFERENT;
        }
    }
}
