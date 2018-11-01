package uk.co.alt236.apkcompare.output.html;

import uk.co.alt236.apkcompare.comparators.results.ComparisonResult;
import uk.co.alt236.apkcompare.comparators.results.ResultBlock;
import uk.co.alt236.apkcompare.comparators.results.comparisons.Comparison;
import uk.co.alt236.apkcompare.comparators.results.comparisons.CompositeResult;
import uk.co.alt236.apkcompare.output.PrintabilityEvaluator;
import uk.co.alt236.apkcompare.output.html.builder.HtmlBuilder;
import uk.co.alt236.apkcompare.output.html.builder.HtmlTable;
import uk.co.alt236.apkcompare.output.writer.FileWriter;
import uk.co.alt236.apkcompare.output.writer.Writer;
import uk.co.alt236.apkcompare.util.FileSizeFormatter;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class HtmlResultsPrinter {

    private final boolean verbose;
    private final FileSizeFormatter fileSizeFormatter;
    private final PrintabilityEvaluator printabilityEvaluator;

    public HtmlResultsPrinter(final FileSizeFormatter fileSizeFormatter,
                              final boolean verbose) {
        this.verbose = verbose;
        this.fileSizeFormatter = fileSizeFormatter;
        this.printabilityEvaluator = new PrintabilityEvaluator();
    }

    public void print(final List<ComparisonResult> results,
                      final File file) {
        final HtmlBuilder builder = new HtmlBuilder();

        builder.startDocument();

        builder.startHead();
        builder.addStyle(new CssGetter().getStyleSheet());
        builder.endHead();

        for (final ComparisonResult result : results) {
            print(builder, result, 0);
        }

        builder.endDocument();

        final Writer writer = new FileWriter(file);
        writer.outln(builder.toString());
        writer.close();
    }

    private void print(final HtmlBuilder builder, final ComparisonResult item, final int indentLevel) {
        if (item instanceof Comparison) {
            print(builder, (Comparison) item, indentLevel);
        } else if (item instanceof ResultBlock) {
            printTitle(builder, item, indentLevel);
            for (final ComparisonResult child : ((ResultBlock) item).getComparisonResults()) {
                print(builder, child, indentLevel + 1);
            }
        } else if (item instanceof CompositeResult) {
            print(builder, (CompositeResult) item, indentLevel);
        } else {
            throw new IllegalStateException("Don't know how to handle: " + item);
        }
    }

    private void print(final HtmlBuilder builder,
                       final CompositeResult item,
                       final int indentLevel) {
        if (!printabilityEvaluator.isPrintable(item, verbose)) {
            return;
        }

        printTitle(builder, item, indentLevel);
        printItemValues(builder, item.getComparisons());
    }

    private void print(final HtmlBuilder builder,
                       final Comparison item,
                       final int indentLevel) {
        if (!printabilityEvaluator.isPrintable(item, verbose)) {
            return;
        }

        printTitle(builder, item, indentLevel);
        printItemValues(builder, Collections.singletonList(item));
    }


    private void printTitle(final HtmlBuilder builder,
                            final ComparisonResult item,
                            final int indentLevel) {
        if (!printabilityEvaluator.isPrintable(item, verbose)) {
            return;
        }

        final String finalText = item.getTitle();
        builder.addHeader(finalText, indentLevel + 1);
    }

    private void printItemValues(final HtmlBuilder builder,
                                 final List<Comparison> comparisons) {

        final HtmlTable table = new ItemValueTableBuilder(fileSizeFormatter, verbose).createComparisonTable(comparisons);
        builder.addTable(table);
    }
}
