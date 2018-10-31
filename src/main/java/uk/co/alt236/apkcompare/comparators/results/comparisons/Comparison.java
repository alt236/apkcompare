package uk.co.alt236.apkcompare.comparators.results.comparisons;

import uk.co.alt236.apkcompare.comparators.results.ComparisonResult;

import javax.annotation.Nullable;

public interface Comparison extends ComparisonResult {

    @Nullable
    String getValue1AsString();

    @Nullable
    String getValue2AsString();

    @Nullable
    String getComparedAttribute();

}
