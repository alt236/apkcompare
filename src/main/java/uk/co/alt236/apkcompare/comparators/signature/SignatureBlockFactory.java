package uk.co.alt236.apkcompare.comparators.signature;

import uk.co.alt236.apkcompare.comparators.results.ComparisonResult;
import uk.co.alt236.apkcompare.comparators.results.ResultBlock;
import uk.co.alt236.apkcompare.comparators.results.comparisons.Comparison;
import uk.co.alt236.apkcompare.comparators.results.comparisons.DateComparison;
import uk.co.alt236.apkcompare.comparators.results.comparisons.TypedComparison;
import uk.co.alt236.apkcompare.repo.signature.SigningCertificate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class SignatureBlockFactory {
    List<ComparisonResult> createSignatureInfoBlock(SignatureInfoProvider apk1,
                                                    SignatureInfoProvider apk2) {
        final List<ComparisonResult> retVal = new ArrayList<>();
        final Comparison signed = new TypedComparison<>(
                "Signature present",
                null,
                apk1.isSigned(),
                apk2.isSigned());

        final Comparison hasValidSignature = new TypedComparison<>(
                "Signature valid",
                null,
                apk1.isSignatureValid(),
                apk2.isSignatureValid());

        final Comparison certificateCount = new TypedComparison<>(
                "Certificate count", null,
                apk1.getCertificates().size(),
                apk2.getCertificates().size());

        final ResultBlock resultBlock = new ResultBlock(
                "Info",
                Arrays.asList(signed, hasValidSignature, certificateCount));

        retVal.add(resultBlock);

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

            final List<Comparison> comparisons = compareCertificates(cert1, cert2);
            blockList.add(new ResultBlock("Certificate " + (i + 1), comparisons));
        }

        return blockList;
    }

    private List<Comparison> compareCertificates(SigningCertificate cert1,
                                                 SigningCertificate cert2) {

        final List<Comparison> retVal = new ArrayList<>();

        retVal.add(new TypedComparison<>(
                "Subject",
                null,
                cert1 == null ? null : cert1.getSubjectDN(),
                cert2 == null ? null : cert2.getSubjectDN()));
        retVal.add(new TypedComparison<>(
                "Issuer",
                null,
                cert1 == null ? null : cert1.getIssuerDN(),
                cert2 == null ? null : cert2.getIssuerDN()));
        retVal.add(new TypedComparison<>(
                "Serial",
                null,
                cert1 == null ? null : cert1.getSerialNumber(),
                cert2 == null ? null : cert2.getSerialNumber()));
        retVal.add(new TypedComparison<>(
                "Algorithm",
                null,
                cert1 == null ? null : cert1.getSigAlgName(),
                cert2 == null ? null : cert2.getSigAlgName()));
        retVal.add(new DateComparison(
                "Valid from",
                null,
                cert1 == null ? null : cert1.getNotBefore(),
                cert2 == null ? null : cert2.getNotBefore()));
        retVal.add(new DateComparison(
                "Valid to",
                null,
                cert1 == null ? null : cert1.getNotAfter(),
                cert2 == null ? null : cert2.getNotAfter()));
        retVal.add(new TypedComparison<>(
                "MD5 Thumb",
                null,
                cert1 == null ? null : cert1.getMd5Thumbprint(),
                cert2 == null ? null : cert2.getMd5Thumbprint()));
        retVal.add(new TypedComparison<>(
                "SHA1 Thumb",
                null,
                cert1 == null ? null : cert1.getSha1Thumbprint(),
                cert2 == null ? null : cert2.getSha1Thumbprint()));
        retVal.add(new TypedComparison<>(
                "SHA256 Thumb",
                null,
                cert1 == null ? null : cert1.getSha256Thumbprint(),
                cert2 == null ? null : cert2.getSha256Thumbprint()));

        return retVal;
    }

    interface SignatureInfoProvider {

        boolean isSigned();

        boolean isSignatureValid();

        List<SigningCertificate> getCertificates();

    }
}
