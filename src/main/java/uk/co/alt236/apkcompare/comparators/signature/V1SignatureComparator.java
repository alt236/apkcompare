package uk.co.alt236.apkcompare.comparators.signature;

import uk.co.alt236.apkcompare.apk.Apk;
import uk.co.alt236.apkcompare.comparators.results.ResultSection;
import uk.co.alt236.apkcompare.repo.signature.SigningCertificate;

import java.util.ArrayList;
import java.util.List;

class V1SignatureComparator {
    private final SignatureBlockFactory signatureBlockFactory;

    V1SignatureComparator() {
        signatureBlockFactory = new SignatureBlockFactory();
    }

    public List<ResultSection> compare(Apk apk1, Apk apk2) {
        final List<ResultSection> retVal = new ArrayList<>();

        final SignatureBlockFactory.SignatureInfoProvider provider1 = createProvider(apk1);
        final SignatureBlockFactory.SignatureInfoProvider provider2 = createProvider(apk2);

        retVal.add(new ResultSection("V1 Signing Info",
                signatureBlockFactory.createSignatureInfoBlock(provider1, provider2)));
        retVal.add(new ResultSection("V1 Signature Comparison",
                signatureBlockFactory.createSignatureComparisonBlock(provider1, provider2)));
        return retVal;
    }


    private SignatureBlockFactory.SignatureInfoProvider createProvider(final Apk apk) {
        return new SignatureBlockFactory.SignatureInfoProvider() {
            @Override
            public boolean isSigned() {
                return apk.isV1Signed();
            }

            @Override
            public boolean isSignatureValid() {
                return apk.isV1SignatureValid();
            }

            @Override
            public List<SigningCertificate> getCertificates() {
                return apk.getV1Certificates();
            }
        };
    }
}
