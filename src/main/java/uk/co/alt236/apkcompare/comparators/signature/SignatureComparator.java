package uk.co.alt236.apkcompare.comparators.signature;

import uk.co.alt236.apkcompare.apk.Apk;
import uk.co.alt236.apkcompare.comparators.ApkComparator;
import uk.co.alt236.apkcompare.comparators.results.ResultSection;

import java.util.ArrayList;
import java.util.List;

public class SignatureComparator implements ApkComparator {
    private final V1SignatureComparator v1SignatureComparator;
    private final V2SignatureComparator v2SignatureComparator;

    public SignatureComparator() {
        v1SignatureComparator = new V1SignatureComparator();
        v2SignatureComparator = new V2SignatureComparator();
    }

    @Override
    public List<ResultSection> compare(Apk apk1, Apk apk2) {
        final List<ResultSection> retVal = new ArrayList<>();

        retVal.addAll(v1SignatureComparator.compare(apk1, apk2));
        retVal.addAll(v2SignatureComparator.compare(apk1, apk2));

        return retVal;
    }
}