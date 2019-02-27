package uk.co.alt236.apkcompare.app.comparators.signature;

import uk.co.alt236.apk.repo.signature.SigningCertificate;
import uk.co.alt236.apkcompare.app.comparators.results.ComparisonResult;
import uk.co.alt236.apkcompare.app.comparators.results.comparisons.CompositeResult;
import uk.co.alt236.apkcompare.app.comparators.results.comparisons.DateComparison;
import uk.co.alt236.apkcompare.app.comparators.results.comparisons.TypedComparison;

import java.util.ArrayList;
import java.util.List;

class SignatureBlockFactory {
    List<ComparisonResult> createSignatureInfoBlock(SignatureInfoProvider apk1,
                                                    SignatureInfoProvider apk2) {
        final List<ComparisonResult> retVal = new ArrayList<>();

        final String title = "Validity";

        final CompositeResult result = new CompositeResult.Builder()
                .withTitle(title)
                .withComparison(new TypedComparison<>(
                        title,
                        "Signature present",
                        apk1.isSigned(),
                        apk2.isSigned()))
                .withComparison(new TypedComparison<>(
                        title,
                        "Signature valid",
                        apk1.isSignatureValid(),
                        apk2.isSignatureValid()))
                .withComparison(new TypedComparison<>(
                        title,
                        "Certificate count",
                        apk1.getCertificates().size(),
                        apk2.getCertificates().size()))
                .build();


        retVal.add(result);
        return retVal;
    }

    List<ComparisonResult> createSignatureComparisonBlock(SignatureInfoProvider apk1,
                                                          SignatureInfoProvider apk2) {
        final List<SigningCertificate> certificates1 = apk1.getCertificates();
        final List<SigningCertificate> certificates2 = apk2.getCertificates();

        return compareCertificateList(certificates1, certificates2);
    }

    private List<ComparisonResult> compareCertificateList(List<SigningCertificate> certificates1,
                                                          List<SigningCertificate> certificates2) {


        final List<ComparisonResult> blockList = new ArrayList<>();


        final int maxSize = Math.max(certificates1.size(), certificates2.size());

        for (int i = 0; i < maxSize; i++) {
            final SigningCertificate cert1;
            final SigningCertificate cert2;

            if (i < certificates1.size()) {
                cert1 = certificates1.get(i);
            } else {
                cert1 = null;
            }

            if (i < certificates2.size()) {
                cert2 = certificates2.get(i);
            } else {
                cert2 = null;
            }

            final CompositeResult compositeResult = compareCertificates("Certificate (" + (i + 1) + "/" + maxSize + ")", cert1, cert2);
            blockList.add(compositeResult);
        }

        return blockList;
    }

    private CompositeResult compareCertificates(final String title,
                                                SigningCertificate cert1,
                                                SigningCertificate cert2) {

        return new CompositeResult.Builder()
                .withTitle(title)
                .withComparison(new TypedComparison<>(
                        title,
                        "Subject",
                        cert1 == null ? null : cert1.getSubjectDN(),
                        cert2 == null ? null : cert2.getSubjectDN()))
                .withComparison(new TypedComparison<>(
                        title,
                        "Issuer",
                        cert1 == null ? null : cert1.getIssuerDN(),
                        cert2 == null ? null : cert2.getIssuerDN()))
                .withComparison(new TypedComparison<>(
                        title,
                        "Serial",
                        cert1 == null ? null : cert1.getSerialNumber(),
                        cert2 == null ? null : cert2.getSerialNumber()))
                .withComparison(new TypedComparison<>(
                        title,
                        "Algorithm",
                        cert1 == null ? null : cert1.getSigAlgName(),
                        cert2 == null ? null : cert2.getSigAlgName()))
                .withComparison(new DateComparison(
                        title,
                        "Valid from",
                        cert1 == null ? null : cert1.getNotBefore(),
                        cert2 == null ? null : cert2.getNotBefore()))
                .withComparison(new DateComparison(
                        title,
                        "Valid to",
                        cert1 == null ? null : cert1.getNotAfter(),
                        cert2 == null ? null : cert2.getNotAfter()))
                .withComparison(new TypedComparison<>(
                        title,
                        "MD5 Thumb",
                        cert1 == null ? null : cert1.getMd5Thumbprint(),
                        cert2 == null ? null : cert2.getMd5Thumbprint()))
                .withComparison(new TypedComparison<>(
                        title,
                        "SHA1 Thumb",
                        cert1 == null ? null : cert1.getSha1Thumbprint(),
                        cert2 == null ? null : cert2.getSha1Thumbprint()))
                .withComparison(new TypedComparison<>(
                        title,
                        "SHA256 Thumb",
                        cert1 == null ? null : cert1.getSha256Thumbprint(),
                        cert2 == null ? null : cert2.getSha256Thumbprint()))
                .build();
    }

    interface SignatureInfoProvider {

        boolean isSigned();

        boolean isSignatureValid();

        List<SigningCertificate> getCertificates();

    }
}
