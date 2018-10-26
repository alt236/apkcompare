package uk.co.alt236.apkcompare.comparators.results;

import javax.annotation.Nullable;

public interface ResultItem extends ComparisonResult {

    @Nullable
    String getValue1AsString();

    @Nullable
    String getValue2AsString();

    @Nullable
    String getComparedAttribute();

}
