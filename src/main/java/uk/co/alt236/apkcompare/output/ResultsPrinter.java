package uk.co.alt236.apkcompare.output;

import uk.co.alt236.apkcompare.comparators.results.ComparisonResult;
import uk.co.alt236.apkcompare.comparators.results.ResultBlock;
import uk.co.alt236.apkcompare.comparators.results.Similarity;
import uk.co.alt236.apkcompare.comparators.results.comparisons.ByteCountComparison;
import uk.co.alt236.apkcompare.comparators.results.comparisons.Comparison;
import uk.co.alt236.apkcompare.output.logging.Logger;
import uk.co.alt236.apkcompare.util.Colorizer;
import uk.co.alt236.apkcompare.util.FileSizeFormatter;

import java.util.List;

public class ResultsPrinter {
    private static final String MISSING_VALUE = "*** MISSING ***";

    private final FileSizeFormatter fileSizeFormatter;
    private final Colorizer colorizer;
    private final boolean verbose;
    private final String colouredMissingValue;
    private final IndentGiver indentGiver;

    public ResultsPrinter(FileSizeFormatter fileSizeFormatter,
                          Colorizer colorizer,
                          boolean verbose) {

        this.indentGiver = new IndentGiver();
        this.fileSizeFormatter = fileSizeFormatter;
        this.colorizer = colorizer;
        this.verbose = verbose;
        this.colouredMissingValue = colorizer.important(MISSING_VALUE);
    }

    public void print(List<ComparisonResult> results) {
        for (final ComparisonResult result : results) {
            print(result, 0);
        }
    }

    private void print(final ComparisonResult item,
                       final int indentLevel) {
        if (item instanceof Comparison) {
            print((Comparison) item, indentLevel);
        } else if (item instanceof ResultBlock) {
            printTitle(item, indentGiver.getIndentation(indentLevel));
            for (final ComparisonResult child : ((ResultBlock) item).getComparisonResults()) {
                print(child, indentLevel + 1);
            }
        } else {
            throw new IllegalStateException("Don't know how to handle: " + item);
        }
    }

    private void print(final Comparison item,
                       final int indentLevel) {
        if (!isPrintable(item)) {
            return;
        }

        printTitle(item, indentGiver.getIndentation(indentLevel));
        printItemValues(item, indentGiver.getIndentation(indentLevel + 1));
    }


    private void printTitle(final ComparisonResult item,
                            final String indent) {
        if (!isPrintable(item)) {
            return;
        }

        final String finalText;

        if (item.getSimilarity() == Similarity.IDENTICAL) {
            finalText = indent + item.getTitle();
        } else {
            finalText = colorizer.error(indent + item.getTitle());
        }

        Logger.get().out(finalText);

    }

    private void printItemValues(final Comparison item,
                                 final String indent) {
        if (!isPrintable(item)) {
            return;
        }

        final String value1;
        final String value2;

        if (item instanceof ByteCountComparison) {
            final ByteCountComparison byteRsult = (ByteCountComparison) item;
            final String comparedAttribute = byteRsult.getComparedAttribute() == null ? "" : byteRsult.getComparedAttribute() + ": ";

            value1 = (byteRsult.getValue1() == null ? colouredMissingValue : comparedAttribute + fileSizeFormatter.format(byteRsult.getValue1()));
            value2 = (byteRsult.getValue2() == null ? colouredMissingValue : comparedAttribute + fileSizeFormatter.format(byteRsult.getValue2()));
        } else {
            value1 = (item.getValue1AsString() == null ? colouredMissingValue : item.getValue1AsString());
            value2 = (item.getValue2AsString() == null ? colouredMissingValue : item.getValue2AsString());
        }

        Logger.get().out(indent + "APK 1: " + value1);
        Logger.get().out(indent + "APK 2: " + value2);
    }

    private boolean isPrintable(final ComparisonResult item) {
        final boolean isDifferent = item.getSimilarity() != Similarity.IDENTICAL;
        return isDifferent || verbose;
    }
}
