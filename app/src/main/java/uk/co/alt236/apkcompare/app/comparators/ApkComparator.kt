package uk.co.alt236.apkcompare.app.comparators

import uk.co.alt236.apk.Apk
import uk.co.alt236.apkcompare.app.comparators.results.ComparisonResult

interface ApkComparator {
    val name: String
    fun compare(apk1: Apk, apk2: Apk): List<ComparisonResult>
}