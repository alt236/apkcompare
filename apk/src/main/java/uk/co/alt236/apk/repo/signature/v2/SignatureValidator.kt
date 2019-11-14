package uk.co.alt236.apk.repo.signature.v2

import com.android.apksig.ApkVerifier
import com.android.apksig.apk.ApkUtils
import com.android.apksig.internal.apk.v2.V2SchemeVerifier
import com.android.apksig.internal.util.RandomAccessFileDataSource
import uk.co.alt236.apk.repo.signature.SigningCertificate
import java.io.RandomAccessFile
import java.util.*
import kotlin.collections.ArrayList

internal class SignatureValidator {

    @Throws(V2SchemeVerifier.SignatureNotFoundException::class)
    fun extractCertificates(file: RandomAccessFile): Result {
        val dataSource = RandomAccessFileDataSource(file)

        val zipSections = ApkUtils.findZipSections(dataSource)
        val v2Result = V2SchemeVerifier.verify(dataSource, zipSections)
        val errors = collectErrors(v2Result)

        return Result(extractCertificates(v2Result), errors)
    }

    private fun collectErrors(result: V2SchemeVerifier.Result): List<ApkVerifier.IssueWithParams> {
        val errorList = ArrayList<ApkVerifier.IssueWithParams>()
        errorList.addAll(result.errors)

        for (signer in result.signers) {
            if (signer.containsErrors()) {
                errorList.addAll(signer.errors)
            }
        }

        return Collections.unmodifiableList(errorList)
    }

    private fun extractCertificates(result: V2SchemeVerifier.Result): List<SigningCertificate> {
        val retVal = ArrayList<SigningCertificate>()
        for (signerInfo in result.signers) {
            for (cert in signerInfo.certs) {
                retVal.add(SigningCertificate(cert))
            }
        }
        return retVal
    }

    data class Result(val certificates: List<SigningCertificate>, val errors: List<ApkVerifier.IssueWithParams>) {
        fun containsErrors(): Boolean = errors.isNotEmpty()
    }
}