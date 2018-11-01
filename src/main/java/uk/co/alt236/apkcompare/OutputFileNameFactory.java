package uk.co.alt236.apkcompare;

import uk.co.alt236.apkcompare.apk.Apk;

class OutputFileNameFactory {
    private static final String SANITISATION_REGEXP = "[^A-Za-z0-9_]";

    public String getFileNameForHtmlReport(final Apk apk1, final Apk apk2) {
        return "compare_"
                + sanitise(apk1)
                + "_with_"
                + sanitise(apk2)
                + ".html";
    }

    private String sanitise(final Apk apk) {
        return apk.getFile().getName().replaceAll(SANITISATION_REGEXP, "_");
    }
}
