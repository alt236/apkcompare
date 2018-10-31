package uk.co.alt236.apkcompare.comparators.results;

import org.apache.commons.codec.binary.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class TypedResultItem<T> implements ResultItem {

    private final String title;
    private final T value1;
    private final T value2;
    private final String comparedAttribute;

    public TypedResultItem(@Nonnull String title,
                           @Nullable String comparedAttribute,
                           @Nullable T value1,
                           @Nullable T value2) {
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
    public T getValue1() {
        return value1;
    }

    @Nullable
    public T getValue2() {
        return value2;
    }

    @Override
    public Similarity getSimilarity() {
        if (value1 instanceof String && value2 instanceof String) {
            if (StringUtils.equals((String) value1, (String) value2)) {
                return Similarity.IDENTICAL;
            } else {
                return Similarity.DIFFERENT;
            }
        } else {
            if (Objects.equals(value1, value2)) {
                return Similarity.IDENTICAL;
            } else {
                return Similarity.DIFFERENT;
            }
        }
    }
}
