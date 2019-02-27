package uk.co.alt236.apkcompare.app.comparators.results.comparisons;


import uk.co.alt236.apkcompare.app.comparators.results.ComparisonResult;

import javax.annotation.Nullable;

public interface Comparison extends ComparisonResult {

    @Nullable
    String getValue1AsString();

    @Nullable
    String getValue2AsString();

    @Nullable
    String getComparedAttribute();

}
