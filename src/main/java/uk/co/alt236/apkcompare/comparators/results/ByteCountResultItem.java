package uk.co.alt236.apkcompare.comparators.results;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ByteCountResultItem implements ResultItem {

    private final String title;
    private final Long value1;
    private final Long value2;
    private final String comparedAttribute;

    public ByteCountResultItem(@Nonnull String title,
                               @Nullable String comparedAttribute,
                               @Nullable Long value1,
                               @Nullable Long value2) {
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
        return value1 == null ? null : String.valueOf(value1);
    }

    @Nullable
    @Override
    public String getValue2AsString() {
        return value2 == null ? null : String.valueOf(value2);
    }

    @Nullable
    @Override
    public String getComparedAttribute() {
        return comparedAttribute;
    }

    @Nullable
    public Long getValue1() {
        return value1;
    }

    @Nullable
    public Long getValue2() {
        return value2;
    }

    @Override
    public Similarity getSimilarity() {
        if (equals(value1, value2)) {
            return Similarity.IDENTICAL;
        } else {
            return Similarity.DIFFERENT;
        }
    }

    private boolean equals(Long val1, Long val2) {
        //noinspection NumberEquality
        if (val1 == val2) {
            return true;
        } else if (val1 != null && val2 != null) {
            return val1.equals(val2);
        } else {
            return false;
        }
    }
}