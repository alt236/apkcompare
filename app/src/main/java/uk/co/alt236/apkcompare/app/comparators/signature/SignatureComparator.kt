package uk.co.alt236.apkcompare.app.comparators.signature

import uk.co.alt236.apk.Apk
import uk.co.alt236.apkcompare.app.comparators.ApkComparator
import uk.co.alt236.apkcompare.app.comparators.results.ComparisonResult
import java.util.*

class SignatureComparator : ApkComparator {
    private val v1SignatureComparator = V1SignatureComparator()
    private val v2SignatureComparator = V2SignatureComparator()

    override val name: String = "Signature Comparator"

    override fun compare(apk1: Apk, apk2: Apk): List<ComparisonResult> {
        val retVal = ArrayList<ComparisonResult>()

        retVal.addAll(v1SignatureComparator.compare(apk1, apk2))
        retVal.addAll(v2SignatureComparator.compare(apk1, apk2))

        return Collections.unmodifiableList(retVal)
    }
}