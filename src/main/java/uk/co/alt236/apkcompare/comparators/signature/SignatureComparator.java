package uk.co.alt236.apkcompare.comparators.signature;

import uk.co.alt236.apkcompare.apk.Apk;
import uk.co.alt236.apkcompare.comparators.ApkComparator;
import uk.co.alt236.apkcompare.comparators.results.ResultBlock;
import uk.co.alt236.apkcompare.comparators.results.ResultItem;
import uk.co.alt236.apkcompare.comparators.results.ResultSection;
import uk.co.alt236.apkcompare.comparators.results.StringResultItem;
import uk.co.alt236.apkcompare.repo.signature.SigningCertificate;
import uk.co.alt236.apkcompare.util.date.IsoISO8601DateParser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignatureComparator implements ApkComparator {

    @Override
    public List<ResultSection> compare(Apk apk1, Apk apk2) {
        final List<ResultSection> retVal = new ArrayList<>();
        final List<SigningCertificate> certificates1 = apk1.getCertificates();
        final List<SigningCertificate> certificates2 = apk2.getCertificates();


        final List<ResultBlock> signatureComparison = compareCertificateList(certificates1, certificates2);
        retVal.add(new ResultSection("Certificate Comparison", signatureComparison));

        return retVal;
    }


    private List<ResultBlock> compareCertificateList(List<SigningCertificate> certificates1,
                                                     List<SigningCertificate> certificates2) {


        final List<ResultBlock> blockList = new ArrayList<>();


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

            final List<ResultItem> resultItems = compareCertificates(cert1, cert2);
            blockList.add(new ResultBlock("Certificate " + (i + 1), resultItems));
        }

        return blockList;
    }


    private List<ResultItem> compareCertificates(SigningCertificate cert1,
                                                 SigningCertificate cert2) {

        final List<ResultItem> retVal = new ArrayList<>();

        retVal.add(new StringResultItem(
                "Subject",
                null,
                cert1 == null ? null : cert1.getSubjectDN().toString(),
                cert2 == null ? null : cert2.getSubjectDN().toString()));
        retVal.add(new StringResultItem(
                "Issuer",
                null,
                cert1 == null ? null : cert1.getIssuerDN().toString(),
                cert2 == null ? null : cert2.getIssuerDN().toString()));
        retVal.add(new StringResultItem(
                "Serial",
                null,
                cert1 == null ? null : cert1.getSerialNumber().toString(),
                cert2 == null ? null : cert2.getSerialNumber().toString()));
        retVal.add(new StringResultItem(
                "Algorithm",
                null,
                cert1 == null ? null : cert1.getSigAlgName(),
                cert2 == null ? null : cert2.getSigAlgName()));
        retVal.add(new StringResultItem(
                "Valid from",
                null,
                cert1 == null ? null : toIsoDate(cert1.getNotBefore()),
                cert2 == null ? null : toIsoDate(cert2.getNotBefore())));
        retVal.add(new StringResultItem(
                "Valid to",
                null,
                cert1 == null ? null : toIsoDate(cert1.getNotAfter()),
                cert2 == null ? null : toIsoDate(cert2.getNotAfter())));
        retVal.add(new StringResultItem(
                "MD5 Thumb",
                null,
                cert1 == null ? null : cert1.getMd5Thumbprint(),
                cert2 == null ? null : cert2.getMd5Thumbprint()));
        retVal.add(new StringResultItem(
                "SHA1 Thumb",
                null,
                cert1 == null ? null : cert1.getSha1Thumbprint(),
                cert2 == null ? null : cert2.getSha1Thumbprint()));
        retVal.add(new StringResultItem(
                "SHA256 Thumb",
                null,
                cert1 == null ? null : cert1.getSha256Thumbprint(),
                cert2 == null ? null : cert2.getSha256Thumbprint()));

        return retVal;
    }

    private String toIsoDate(final Date date) {
        return IsoISO8601DateParser.toIsoDateString(date);
    }

}