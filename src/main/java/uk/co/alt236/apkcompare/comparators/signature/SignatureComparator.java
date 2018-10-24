package uk.co.alt236.apkcompare.comparators.signature;

import uk.co.alt236.apkcompare.apk.Apk;
import uk.co.alt236.apkcompare.comparators.ApkComparator;
import uk.co.alt236.apkcompare.comparators.ResultBlock;
import uk.co.alt236.apkcompare.comparators.ResultItem;
import uk.co.alt236.apkcompare.comparators.ResultSection;
import uk.co.alt236.apkcompare.repo.signature.SigningCertificate;
import uk.co.alt236.apkcompare.util.Colorizer;
import uk.co.alt236.apkcompare.util.FileSizeFormatter;
import uk.co.alt236.apkcompare.util.date.IsoISO8601DateParser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignatureComparator implements ApkComparator {
    public SignatureComparator(FileSizeFormatter fileSizeFormatter, Colorizer colorizer, boolean verbose) {

    }

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


        if (certificates1.size() == certificates2.size()) {
            compareSameSizeCertificateLists(blockList, certificates1, certificates2);
        } else {
            throw new IllegalStateException("Unimplemented");
        }

        return blockList;
    }

    private void compareSameSizeCertificateLists(List<ResultBlock> blockList,
                                                 List<SigningCertificate> certificates1,
                                                 List<SigningCertificate> certificates2) {


        for (int i = 0; i < certificates1.size(); i++) {
            final SigningCertificate cert1 = certificates1.get(i);
            final SigningCertificate cert2 = certificates2.get(i);
            final List<ResultItem> resultItems = compareCertificates(cert1, cert2);

            blockList.add(new ResultBlock("Certificate " + (i + 1), resultItems));
        }
    }


    private List<ResultItem> compareCertificates(SigningCertificate cert1,
                                                 SigningCertificate cert2) {

        final List<ResultItem> retVal = new ArrayList<>();

        retVal.add(new ResultItem("Subject", cert1.getSubjectDN().toString(), cert2.getSubjectDN().toString()));
        retVal.add(new ResultItem("Issuer", cert1.getIssuerDN().toString(), cert2.getIssuerDN().toString()));
        retVal.add(new ResultItem("Serial", cert1.getSerialNumber().toString(), cert2.getSerialNumber().toString()));
        retVal.add(new ResultItem("Algorithm", cert1.getSigAlgName(), cert2.getSigAlgName()));
        retVal.add(new ResultItem("Valid from", toIsoDate(cert1.getNotBefore()), toIsoDate(cert2.getNotBefore())));
        retVal.add(new ResultItem("Valid to", toIsoDate(cert1.getNotAfter()), toIsoDate(cert2.getNotAfter())));
        retVal.add(new ResultItem("MD5 Thumb", cert1.getMd5Thumbprint(), cert2.getMd5Thumbprint()));
        retVal.add(new ResultItem("SHA1 Thumb", cert1.getSha1Thumbprint(), cert2.getSha1Thumbprint()));
        retVal.add(new ResultItem("SHA256 Thumb", cert1.getSha256Thumbprint(), cert2.getSha256Thumbprint()));

        return retVal;
    }

    private String toIsoDate(final Date date) {
        return IsoISO8601DateParser.toIsoDateString(date);
    }

}