package uk.co.alt236.apkcompare;

import uk.co.alt236.apkcompare.apk.Apk;
import uk.co.alt236.apkcompare.output.InputFiles;

class OutputFileNameFactory {
    private static final String SANITISATION_REGEXP = "[^A-Za-z0-9_]";

    public String getFileNameForHtmlReport(InputFiles inputFiles) {
        return "compare_"
                + sanitise(inputFiles.getApk1())
                + "_with_"
                + sanitise(inputFiles.getApk2())
                + ".html";
    }

    private String sanitise(final Apk apk) {
        return apk.getFile().getName().replaceAll(SANITISATION_REGEXP, "_");
    }
}
