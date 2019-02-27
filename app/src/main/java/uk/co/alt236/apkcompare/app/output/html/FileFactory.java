package uk.co.alt236.apkcompare.app.output.html;

import uk.co.alt236.apkcompare.app.comparators.results.ComparisonResult;

import java.io.File;

class FileFactory {
    private static final String FOLDER_SUFFX = "_files";
    private static final String FILE_PROTOCOL = "file:/";

    public File getResultHtmlFile(final ComparisonResult result,
                                  final File rootFile) {
        final String newFilename = result.getTitle().replaceAll("[^\\w.-]", "_");
        return new File(rootFile + FOLDER_SUFFX, newFilename + ".html");
    }


    public String getRelativeLink(final ComparisonResult result,
                                  final File rootFile) {
        final File resultFile = getResultHtmlFile(result, rootFile);
        return resultFile.toString().replace("./", "");
    }

}
