package uk.co.alt236.apkcompare.app.comparators.file

import uk.co.alt236.apk.Apk
import uk.co.alt236.apk.util.Hasher
import uk.co.alt236.apkcompare.app.comparators.ApkComparator
import uk.co.alt236.apkcompare.app.comparators.results.ComparisonResult
import uk.co.alt236.apkcompare.app.comparators.results.ResultBlock
import uk.co.alt236.apkcompare.app.comparators.results.comparisons.CompositeResult
import uk.co.alt236.apkcompare.app.comparators.results.comparisons.TypedComparison
import java.util.*

class FileComparator : ApkComparator {
    private val hasher = Hasher()

    override val name = "File Comparator"

    override fun compare(apk1: Apk, apk2: Apk): List<ComparisonResult> {
        val retVal = ArrayList<ComparisonResult>()

        val signatureComparison = compareFiles(apk1, apk2)
        retVal.add(ResultBlock("File Comparison", signatureComparison))

        return retVal
    }

    private fun compareFiles(apk1: Apk, apk2: Apk): List<ComparisonResult> {
        val retVal = ArrayList<ComparisonResult>()

        val compositeResult = CompositeResult.Builder()
                .withTitle("File Hashes")
                .withComparison(TypedComparison("File Hashes", "MD5", hasher.md5Hex(apk1.file), hasher.md5Hex(apk2.file)))
                .withComparison(TypedComparison("File Hashes", "SHA1", hasher.sha1Hex(apk1.file), hasher.sha1Hex(apk2.file)))
                .withComparison(TypedComparison("File Hashes", "SHA256", hasher.sha256Hex(apk1.file), hasher.sha256Hex(apk2.file)))
                .build()

        retVal.add(compositeResult)
        return retVal
    }
}
