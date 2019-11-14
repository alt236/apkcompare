package uk.co.alt236.apk.zip

import java.util.zip.ZipEntry

data class Entry(val name: String, val isDirectory: Boolean, val fileSize: Long) {

    constructor(zipEntry: ZipEntry) : this(zipEntry.name, zipEntry.isDirectory, zipEntry.size)

}