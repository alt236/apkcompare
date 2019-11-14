package uk.co.alt236.apk.util

import org.apache.commons.codec.digest.DigestUtils
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class Hasher {

    fun md5Hex(input: ByteArray): String {
        return DigestUtils.md5Hex(input)
    }

    fun md5Hex(input: File): String {
        val retVal: String
        val fis = FileInputStream(input)
        retVal = DigestUtils.md5Hex(fis)
        fis.close()

        return retVal
    }

    fun sha1Hex(input: ByteArray): String {
        return DigestUtils.sha1Hex(input)
    }

    fun sha1Hex(input: File): String {
        val retVal: String
        val fis = FileInputStream(input)
        retVal = DigestUtils.sha1Hex(fis)
        fis.close()
        return retVal
    }

    fun sha256Hex(input: String): String {
        return DigestUtils.sha256Hex(input)
    }

    fun sha256Hex(input: ByteArray): String {
        return DigestUtils.sha256Hex(input)
    }

    fun sha256Hex(input: File): String {
        val retVal: String
        val fis = FileInputStream(input)
        retVal = DigestUtils.sha256Hex(fis)
        fis.close()
        return retVal
    }

    fun sha256Hex(inputStream: InputStream): String {
        val retVal = DigestUtils.sha256Hex(inputStream)
        inputStream.close()
        return retVal
    }

}
