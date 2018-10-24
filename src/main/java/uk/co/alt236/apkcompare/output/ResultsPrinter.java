package uk.co.alt236.apkcompare.output;

import uk.co.alt236.apkcompare.comparators.*;
import uk.co.alt236.apkcompare.output.logging.Logger;
import uk.co.alt236.apkcompare.util.Colorizer;
import uk.co.alt236.apkcompare.util.FileSizeFormatter;

import java.util.List;

public class ResultsPrinter {
    private static final String MISSING_VALUE = "[ MISSING ]";
    private static final String LEVEL_1_INDENT = "";
    private static final String LEVEL_2_INDENT = "\t";
    private static final String LEVEL_3_INDENT = "\t\t";
    private static final String LEVEL_4_INDENT = "\t\t\t";

    private final FileSizeFormatter fileSizeFormatter;
    private final Colorizer colorizer;
    private final boolean verbose;

    public ResultsPrinter(FileSizeFormatter fileSizeFormatter,
                          Colorizer colorizer,
                          boolean verbose) {

        this.fileSizeFormatter = fileSizeFormatter;
        this.colorizer = colorizer;
        this.verbose = verbose;
    }

    public void print(List<ResultSection> results) {
        for (final ResultSection section : results) {
            printTitle(section, LEVEL_1_INDENT);

            for (final ResultBlock block : section.getResultBlocks()) {
                printTitle(block, LEVEL_2_INDENT);

                for (final ResultItem item : block.getResultItems()) {
                    if ((verbose && item.getSimilarity() == Similarity.IDENTICAL)
                            || item.getSimilarity() != Similarity.IDENTICAL) {
                        printTitle(item, LEVEL_3_INDENT);
                        printItemValues(item);
                    }
                }
            }
        }
    }

    private void printTitle(final ComparisonResult result, final String indent) {
        final String finalText;

        if (result.getSimilarity() == Similarity.IDENTICAL) {
            finalText = indent + result.getTitle();
        } else {
            finalText = colorizer.error(indent + result.getTitle());
        }

        Logger.get().out(finalText);
    }

    private void printItemValues(ResultItem item) {
        Logger.get().out(LEVEL_4_INDENT + "APK 1: " + (item.getValue1AsString() == null ? MISSING_VALUE : item.getValue1AsString()));
        Logger.get().out(LEVEL_4_INDENT + "APK 2: " + (item.getValue2AsString() == null ? MISSING_VALUE : item.getValue2AsString()));
    }
}
