package uk.co.alt236.apk.repo.signature;


import uk.co.alt236.apk.zip.Entry;

import java.util.Collections;
import java.util.List;

public class ValidationResult {
    private final SignatureStatus signatureStatusStatus;
    private final List<Entry> failedEntries;


    public ValidationResult(final SignatureStatus signatureStatusStatus,
                            final List<Entry> result) {
        this.signatureStatusStatus = signatureStatusStatus;
        this.failedEntries = result;
    }

    public ValidationResult(final SignatureStatus signatureStatusStatus) {
        this(signatureStatusStatus, Collections.emptyList());
    }

    public SignatureStatus getSignatureStatus() {
        return signatureStatusStatus;
    }

    public List<Entry> getFailedEntries() {
        return failedEntries;
    }
}
