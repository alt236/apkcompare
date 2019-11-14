package uk.co.alt236.apkcompare.app.output.html;

import uk.co.alt236.apkcompare.app.comparators.results.ComparisonResult;

import java.io.File;
import java.util.Locale;

class FileFactory {
    private static final String FOLDER_SUFFX = "_files";
    private static final String FILE_PROTOCOL = "file:/";

    File getResultHtmlFile(final ComparisonResult result,
                           final File rootFile) {
        final String newFilename = result.getTitle().replaceAll("[^\\w.-]", "_").toLowerCase(Locale.US);
        return new File(rootFile + FOLDER_SUFFX, newFilename + ".html");
    }


    String getRelativeLink(final ComparisonResult result,
                           final File rootFile) {

        final File resultFile = getResultHtmlFile(result, new File(rootFile.getName()));
        return resultFile.toString().replace("./", "");
    }

}
