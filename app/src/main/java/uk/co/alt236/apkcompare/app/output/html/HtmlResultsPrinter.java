package uk.co.alt236.apkcompare.app.output.html;

import uk.co.alt236.apkcompare.app.comparators.results.ComparisonResult;
import uk.co.alt236.apkcompare.app.comparators.results.MissingValue;
import uk.co.alt236.apkcompare.app.comparators.results.Similarity;
import uk.co.alt236.apkcompare.app.comparators.results.comparisons.Comparison;
import uk.co.alt236.apkcompare.app.comparators.results.groups.CompositeResult;
import uk.co.alt236.apkcompare.app.comparators.results.groups.ResultBlock;
import uk.co.alt236.apkcompare.app.output.InputFiles;
import uk.co.alt236.apkcompare.app.output.PrintabilityEvaluator;
import uk.co.alt236.apkcompare.app.output.html.builder.doc.HtmlBuilder;
import uk.co.alt236.apkcompare.app.output.html.builder.doc.HtmlLink;
import uk.co.alt236.apkcompare.app.output.html.builder.table.HtmlTable;
import uk.co.alt236.apkcompare.app.output.html.builder.table.TableRow;
import uk.co.alt236.apkcompare.app.output.html.builder.table.cells.Cell;
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

    public HtmlResultsPrinter(final FileSizeFormatter fileSizeFormatter,
                              final boolean verbose,
                              final boolean prettyHtml) {
        this.verbose = verbose;
        this.prettyHtml = prettyHtml;
        this.fileSizeFormatter = fileSizeFormatter;
        this.printabilityEvaluator = new PrintabilityEvaluator();
    }

    public void print(final List<ComparisonResult> results,
                      final InputFiles inputFiles,
                      final File rootFile) {
        final FileFactory fileFactory = new FileFactory(rootFile);

        createRootFile(fileFactory, inputFiles, results);

        for (final ComparisonResult result : results) {
            createResultFile(fileFactory, inputFiles, result);
        }
    }


    private void createRootFile(final FileFactory fileFactory,
                                final InputFiles inputFiles,
                                final List<ComparisonResult> results) {
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

            final File comparisonFile = fileFactory.getResultHtmlFile(result);
            final String fileLink = fileFactory.getLinkRelativeToRoot(result);
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

        final Writer writer = new FileWriter(fileFactory.getRootFile());
        writer.outln(builder.toString());
        writer.close();
    }

    private void createResultFile(final FileFactory fileFactory,
                                  final InputFiles inputFiles,
                                  final ComparisonResult result) {
        final HtmlBuilder builder = new HtmlBuilder(prettyHtml);

        builder.startDocument();
        builder.startHead();
        builder.addStyle(new CssGetter().getStyleSheet());
        builder.endHead();

        addComparedFilesHeader(builder, inputFiles);

        print(fileFactory, inputFiles, builder, result, 0);

        builder.endDocument();

        final File file = fileFactory.getResultHtmlFile(result);
        final Writer writer = new FileWriter(file);
        writer.outln(builder.toString());
        writer.close();
    }


    private void createResultSubFile(final FileFactory fileFactory,
                                     final InputFiles inputFiles,
                                     final ComparisonResult result,
                                     final File htmlFile) {

        final HtmlBuilder builder = new HtmlBuilder(prettyHtml);
        builder.startDocument();
        builder.startHead();
        builder.addStyle(new CssGetter().getStyleSheet());
        builder.endHead();

        addComparedFilesHeader(builder, inputFiles);

        print(fileFactory, inputFiles, builder, result, 0);

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

    private void print(final FileFactory fileFactory,
                       final InputFiles inputFiles,
                       final HtmlBuilder builder,
                       final ComparisonResult item,
                       final int indentLevel) {

        if (item instanceof Comparison) {
            print(builder, (Comparison) item, indentLevel);
        } else if (item instanceof ResultBlock) {
            printTitle(builder, item, indentLevel);

            final ResultBlock resultBlock = (ResultBlock) item;
            if (resultBlock.getComplex()) {
                addComplexResultBlockToc(fileFactory, builder, resultBlock);

                for (ComparisonResult result : resultBlock.getComparisonResults()) {
                    if (!printabilityEvaluator.isPrintable(item, verbose)) {
                        continue;
                    }

                    final File file = fileFactory.getResultHtmlFile(result);
                    createResultSubFile(fileFactory, inputFiles, result, file);
                }
            } else {
                for (final ComparisonResult child : ((ResultBlock) item).getComparisonResults()) {
                    print(fileFactory, inputFiles, builder, child, indentLevel + 1);
                }
            }
        } else if (item instanceof CompositeResult) {
            print(builder, (CompositeResult) item, indentLevel);
        } else {
            throw new IllegalStateException("Don't know how to handle: " + item);
        }
    }


    private void addComplexResultBlockToc(final FileFactory fileFactory,
                                          final HtmlBuilder builder,
                                          final ResultBlock resultBlock) {

        final List<String> header = Arrays.asList("Similarity", "APK1", "APK2", "Class");
        final HtmlTable.Builder tableBuilder = new HtmlTable.Builder(header.size());
        tableBuilder.setId("comparisons");
        tableBuilder.addRow(TableRow.createHeaderRowFromStrings(header));

        for (ComparisonResult result : resultBlock.getComparisonResults()) {
            if (!printabilityEvaluator.isPrintable(result, verbose)) {
                continue;
            }

            final String fileLink = fileFactory.getLinkRelativeToFiles(result);
            final Similarity similarity = result.getSimilarity();
            final MissingValue missingValue = result.getMissingValue();

            final HtmlLink htmlLink = new HtmlLink(fileLink, result.getTitle());
            final Cell classNameCell = new LinkCell(htmlLink);
            final Cell similarityCell = new StringCell(
                    TableCellIdResolver.getStatusString(similarity),
                    TableCellIdResolver.getIdForSimilarity(similarity));

            final Cell apk1Status = new StringCell(
                    TableCellIdResolver.getMissingValueStringForApk1(missingValue),
                    TableCellIdResolver.getMissingValueIdForApk1(missingValue));
            final Cell apk2Status = new StringCell(
                    TableCellIdResolver.getMissingValueStringForApk2(missingValue),
                    TableCellIdResolver.getMissingValueIdForApk2(missingValue));

            final TableRow tableRow = TableRow.createRowFromCells(similarityCell, apk1Status, apk2Status, classNameCell);

            tableBuilder.addRow(tableRow);
        }
        builder.addTable(tableBuilder.build());
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
