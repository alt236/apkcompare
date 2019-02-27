package uk.co.alt236.apkcompare.app.comparators.signature;

import uk.co.alt236.apk.Apk;
import uk.co.alt236.apk.repo.signature.SigningCertificate;
import uk.co.alt236.apkcompare.app.comparators.results.ComparisonResult;
import uk.co.alt236.apkcompare.app.comparators.results.ResultBlock;

import java.util.ArrayList;
import java.util.List;

class V2SignatureComparator {

    private final SignatureBlockFactory signatureBlockFactory;

    V2SignatureComparator() {
        signatureBlockFactory = new SignatureBlockFactory();
    }

    public List<ComparisonResult> compare(Apk apk1, Apk apk2) {
        final List<ComparisonResult> retVal = new ArrayList<>();

        final SignatureBlockFactory.SignatureInfoProvider provider1 = createProvider(apk1);
        final SignatureBlockFactory.SignatureInfoProvider provider2 = createProvider(apk2);

        retVal.add(new ResultBlock("V2 Signing Info",
                signatureBlockFactory.createSignatureInfoBlock(provider1, provider2)));
        retVal.add(new ResultBlock("V2 Signature Comparison",
                signatureBlockFactory.createSignatureComparisonBlock(provider1, provider2)));
        return retVal;
    }


    private SignatureBlockFactory.SignatureInfoProvider createProvider(final Apk apk) {
        return new SignatureBlockFactory.SignatureInfoProvider() {
            @Override
            public boolean isSigned() {
                return apk.isV2Signed();
            }

            @Override
            public boolean isSignatureValid() {
                return apk.isV2SignatureValid();
            }


            @Override
            public List<SigningCertificate> getCertificates() {
                return apk.getV2Certificates();
            }
        };
    }
}
