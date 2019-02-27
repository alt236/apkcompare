package uk.co.alt236.apkcompare.app.output.html;

import uk.co.alt236.apkcompare.app.comparators.results.ComparisonResult;
import uk.co.alt236.apkcompare.app.comparators.results.ResultBlock;
import uk.co.alt236.apkcompare.app.comparators.results.Similarity;
import uk.co.alt236.apkcompare.app.comparators.results.comparisons.Comparison;
import uk.co.alt236.apkcompare.app.comparators.results.comparisons.CompositeResult;
import uk.co.alt236.apkcompare.app.output.InputFiles;
import uk.co.alt236.apkcompare.app.output.PrintabilityEvaluator;
import uk.co.alt236.apkcompare.app.output.html.builder.doc.HtmlBuilder;
import uk.co.alt236.apkcompare.app.output.html.builder.doc.HtmlLink;
import uk.co.alt236.apkcompare.app.output.html.builder.table.HtmlTable;
import uk.co.alt236.apkcompare.app.output.html.builder.table.TableRow;
import uk.co.alt236.apkcompare.app.output.html.builder.table.cells.LinkCell;
import uk.co.alt236.apkcompare.app.output.html.builder.table.cells.StringCell;
import uk.co.alt236.apkcompare.app.output.writer.FileWriter;
import uk.co.alt236.apkcompare.app.output.writer.Writer;
import uk.co.alt236.apkcompare.app.util.FileSizeFormatter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HtmlResultsPrinter {

    private final boolean verbose;
    private final boolean prettyHtml;
    private final FileSizeFormatter fileSizeFormatter;
    private final PrintabilityEvaluator printabilityEvaluator;
    private final FileFactory fileFactory;

    public HtmlResultsPrinter(final FileSizeFormatter fileSizeFormatter,
                              final boolean verbose,
                              final boolean prettyHtml) {
        this.verbose = verbose;
        this.prettyHtml = prettyHtml;
        this.fileSizeFormatter = fileSizeFormatter;
        this.printabilityEvaluator = new PrintabilityEvaluator();
        this.fileFactory = new FileFactory();
    }

    public void print(final List<ComparisonResult> results,
                      final InputFiles inputFiles,
                      final File rootFile) {
        createRootFile(results, inputFiles, rootFile);

        for (final ComparisonResult result : results) {
            createResultFile(result, inputFiles, fileFactory.getResultHtmlFile(result, rootFile));
        }
    }


    private void createRootFile(final List<ComparisonResult> results,
                                InputFiles inputFiles,
                                final File rootFile) {
        final HtmlBuilder builder = new HtmlBuilder(prettyHtml);

        builder.startDocument();
        builder.startHead();
        builder.addStyle(new CssGetter().getStyleSheet());
        builder.endHead();

        addComparedFilesHeader(builder, inputFiles);

        builder.addHeader("List of comparisons", 1);
        final List<String> header = Arrays.asList("Comparison", "Status");
        final HtmlTable.Builder tableBuilder = new HtmlTable.Builder(header.size());
        tableBuilder.setId("comparisons");
        tableBuilder.addRow(TableRow.createHeaderRowFromStrings(header));

        for (final ComparisonResult result : results) {
            final Similarity similarity = result.getSimilarity();

            final File comparisonFile = fileFactory.getResultHtmlFile(result, rootFile);
            final String fileLink = fileFactory.getRelativeLink(result, rootFile);
            createFile(comparisonFile);

            final HtmlLink htmlLink = new HtmlLink(fileLink, result.getTitle());
            final TableRow tableRow = TableRow.createRowFromCells(Arrays.asList(
                    new LinkCell(htmlLink),
                    new StringCell(
                            TableCellIdResolver.getStatusString(similarity),
                            TableCellIdResolver.getIdForSimilarity(similarity))));

            tableBuilder.addRow(tableRow);
        }

        builder.addTable(tableBuilder.build());
        builder.endDocument();

        final Writer writer = new FileWriter(rootFile);
        writer.outln(builder.toString());
        writer.close();
    }

    private void createResultFile(final ComparisonResult result,
                                  final InputFiles inputFiles,
                                  final File htmlFile) {
        final HtmlBuilder builder = new HtmlBuilder(prettyHtml);

        builder.startDocument();
        builder.startHead();
        builder.addStyle(new CssGetter().getStyleSheet());
        builder.endHead();

        addComparedFilesHeader(builder, inputFiles);

        print(builder, result, 0);

        builder.endDocument();

        final Writer writer = new FileWriter(htmlFile);
        writer.outln(builder.toString());
        writer.close();
    }

    private void addComparedFilesHeader(final HtmlBuilder builder,
                                        final InputFiles inputFiles) {
        builder.addHeader("Comparing the following files", 1);
        final HtmlTable.Builder apkFilesTable = new HtmlTable.Builder(2);
        apkFilesTable.setId("comparisons");
        apkFilesTable.addRow(TableRow.createRowFromStrings("APK1", inputFiles.getApk1().getFile().getAbsolutePath()));
        apkFilesTable.addRow(TableRow.createRowFromStrings("APK2", inputFiles.getApk2().getFile().getAbsolutePath()));
        builder.addTable(apkFilesTable.build());
    }

    private void createFile(final File file) {
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
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
