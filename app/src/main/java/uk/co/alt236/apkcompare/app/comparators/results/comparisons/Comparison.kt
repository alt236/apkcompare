package uk.co.alt236.apkcompare.app.comparators.results.comparisons


import uk.co.alt236.apkcompare.app.comparators.results.ComparisonResult

interface Comparison : ComparisonResult {

    val value1AsString: String?

    val value2AsString: String?

    val comparedAttribute: String?

}
