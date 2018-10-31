package uk.co.alt236.apkcompare.comparators.results.comparisons;

import uk.co.alt236.apkcompare.comparators.results.Similarity;
import uk.co.alt236.apkcompare.util.date.IsoISO8601DateParser;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Date;
import java.util.Objects;

public class DateComparison implements Comparison {

    private final String title;
    private final Date value1;
    private final Date value2;
    private final String comparedAttribute;

    public DateComparison(@Nonnull String title,
                          @Nullable String comparedAttribute,
                          @Nullable Date value1,
                          @Nullable Date value2) {
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
        return value1 == null ? null : toIsoDate(value1);
    }

    @Nullable
    @Override
    public String getValue2AsString() {
        return value2 == null ? null : toIsoDate(value2);
    }

    @Nullable
    @Override
    public String getComparedAttribute() {
        return comparedAttribute;
    }

    @Nullable
    public Date getValue1() {
        return value1;
    }

    @Nullable
    public Date getValue2() {
        return value2;
    }

    @Override
    public Similarity getSimilarity() {
        if (Objects.equals(value1, value2)) {
            return Similarity.IDENTICAL;
        } else {
            return Similarity.DIFFERENT;
        }
    }

    private String toIsoDate(final Date date) {
        return IsoISO8601DateParser.toIsoDateString(date);
    }

}