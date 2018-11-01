package uk.co.alt236.apkcompare.output.console;

import uk.co.alt236.apkcompare.comparators.results.ComparisonResult;
import uk.co.alt236.apkcompare.comparators.results.ResultBlock;
import uk.co.alt236.apkcompare.comparators.results.Similarity;
import uk.co.alt236.apkcompare.comparators.results.comparisons.Comparison;
import uk.co.alt236.apkcompare.comparators.results.comparisons.CompositeResult;
import uk.co.alt236.apkcompare.output.PrintabilityEvaluator;
import uk.co.alt236.apkcompare.output.logging.Logger;
import uk.co.alt236.apkcompare.util.Colorizer;
import uk.co.alt236.apkcompare.util.FileSizeFormatter;

import java.util.List;

public class ConsoleResultsPrinter {

    private final Colorizer colorizer;
    private final boolean verbose;
    private final IndentGiver indentGiver;
    private final ItemValuePrinter itemValuePrinter;
    private final PrintabilityEvaluator printabilityEvaluator;

    public ConsoleResultsPrinter(FileSizeFormatter fileSizeFormatter,
                                 Colorizer colorizer,
                                 boolean verbose) {

        this.indentGiver = new IndentGiver();
        this.colorizer = colorizer;
        this.verbose = verbose;
        this.printabilityEvaluator = new PrintabilityEvaluator();
        this.itemValuePrinter = new ItemValuePrinter(fileSizeFormatter, colorizer, indentGiver);
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
            printTitle(item, indentLevel);
            for (final ComparisonResult child : ((ResultBlock) item).getComparisonResults()) {
                print(child, indentLevel + 1);
            }
        } else if (item instanceof CompositeResult) {
            print((CompositeResult) item, indentLevel);
        } else {
            throw new IllegalStateException("Don't know how to handle: " + item);
        }
    }

    private void print(final CompositeResult item,
                       final int indentLevel) {
        if (!printabilityEvaluator.isPrintable(item, verbose)) {
            return;
        }

        printTitle(item, indentLevel);
        for (final Comparison comparison : item.getComparisons()) {
            printItemValues(comparison, indentLevel + 1, true);
        }
    }

    private void print(final Comparison item,
                       final int indentLevel) {
        if (!printabilityEvaluator.isPrintable(item, verbose)) {
            return;
        }

        printTitle(item, indentLevel);
        printItemValues(item, indentLevel + 1, false);
    }


    private void printTitle(final ComparisonResult item,
                            final int indentLevel) {
        if (!printabilityEvaluator.isPrintable(item, verbose)) {
            return;
        }

        final String finalText;
        final String indent = indentGiver.getIndentation(indentLevel);

        if (item.getSimilarity() == Similarity.IDENTICAL) {
            finalText = indent + item.getTitle();
        } else {
            finalText = colorizer.error(indent + item.getTitle());
        }

        Logger.get().out(finalText);

    }

    private void printItemValues(final Comparison item,
                                 final int indentLevel,
                                 final boolean colourDifferences) {
        if (!printabilityEvaluator.isPrintable(item, verbose)) {
            return;
        }

        itemValuePrinter.printItemValues(item, indentLevel, colourDifferences);
    }
}
