package uk.co.alt236.apkcompare.app.output.html

import uk.co.alt236.apkcompare.app.comparators.results.ComparisonResult
import java.io.File
import java.util.*

internal class FileFactory(val rootFile: File) {
    private val rootFileName: String = rootFile.name

    fun getResultHtmlFile(result: ComparisonResult): File {
        return getResultHtmlFile(result, rootFile)
    }

    fun getLinkRelativeToRoot(result: ComparisonResult): String {
        val resultFile = getResultHtmlFile(result, File(rootFileName))
        return resultFile.toString().replace("./", "")
    }

    fun getLinkRelativeToFiles(result: ComparisonResult): String {
        return "${result.title.sanitise()}.html"
    }

    private fun getResultHtmlFile(result: ComparisonResult, baseFile: File): File {
        val newFilename = result.title.sanitise()
        return File("$baseFile$FOLDER_SUFFIX", "$newFilename.html")
    }


    private fun String.sanitise(): String {
        return this.replace(saneRegex, "_")
                .toLowerCase(Locale.US)
    }

    private companion object {
        private val saneRegex = "[^\\w.-]".toRegex()
        private const val FOLDER_SUFFIX = "_files"
    }
}
