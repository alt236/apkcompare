package uk.co.alt236.apkcompare.app.util

import org.apache.commons.io.FileUtils

class FileSizeFormatter(private val format: Boolean) {

    fun format(size: Long): String {
        return if (format) {
            FileUtils.byteCountToDisplaySize(size)
        } else {
            size.toString()
        }
    }
}
