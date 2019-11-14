package uk.co.alt236.apk.repo.signature

import uk.co.alt236.apk.util.Hasher
import java.math.BigInteger
import java.security.Principal
import java.security.cert.X509Certificate
import java.util.*

class SigningCertificate(private val cert: X509Certificate) {
    private val hasher = Hasher()

    val subjectDN: Principal = cert.subjectDN

    val issuerDN: Principal = cert.issuerDN

    val sigAlgName: String = cert.sigAlgName

    val serialNumber: BigInteger = cert.serialNumber

    val notBefore: Date = cert.notBefore

    val notAfter: Date = cert.notAfter

    val md5Thumbprint: String by lazy { hasher.md5Hex(cert.encoded) }

    val sha1Thumbprint: String by lazy { hasher.sha1Hex(cert.encoded) }

    val sha256Thumbprint: String by lazy { hasher.sha256Hex(cert.encoded) }
}
