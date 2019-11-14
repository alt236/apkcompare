package uk.co.alt236.apk.repo.signature


import uk.co.alt236.apk.zip.Entry

data class ValidationResult @JvmOverloads constructor(val signatureStatus: SignatureStatus,
                                                      val failedEntries: List<Entry> = emptyList())