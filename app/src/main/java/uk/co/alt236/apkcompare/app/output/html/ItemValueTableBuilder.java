package uk.co.alt236.apkcompare.app.output.html;

import uk.co.alt236.apkcompare.app.comparators.results.Similarity;
import uk.co.alt236.apkcompare.app.comparators.results.comparisons.ByteCountComparison;
import uk.co.alt236.apkcompare.app.comparators.results.comparisons.Comparison;
import uk.co.alt236.apkcompare.app.output.PrintabilityEvaluator;
import uk.co.alt236.apkcompare.app.output.html.builder.table.HtmlTable;
import uk.co.alt236.apkcompare.app.output.html.builder.table.TableRow;
import uk.co.alt236.apkcompare.app.output.html.builder.table.cells.Cell;
import uk.co.alt236.apkcompare.app.output.html.builder.table.cells.StringCell;
import uk.co.alt236.apkcompare.app.util.FileSizeFormatter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ItemValueTableBuilder {
    private static final String MISSING_VALUE = "*** MISSING ***";

    private final FileSizeFormatter fileSizeFormatter;
    private final PrintabilityEvaluator printabilityEvaluator;
    private final boolean verbose;

    public ItemValueTableBuilder(FileSizeFormatter fileSizeFormatter,
                                 boolean verbose) {
        this.printabilityEvaluator = new PrintabilityEvaluator();
        this.fileSizeFormatter = fileSizeFormatter;
        this.verbose = verbose;
    }

    public HtmlTable createComparisonTable(final List<Comparison> comparisons) {


        final List<String> header = Arrays.asList("", "Status", "APK1", "APK2");
        final HtmlTable.Builder tableBuilder = new HtmlTable.Builder(header.size());
        tableBuilder.setId("comparisons");

        tableBuilder.addRow(TableRow.createHeaderRowFromStrings(header));

        for (final Comparison item : comparisons) {
            if (!printabilityEvaluator.isPrintable(item, verbose)) {
                continue;
            }

            final String formattedValue1;
            final String formattedValue2;

            final String comparedAttribute = getComparedAttribute(item);

            if (item instanceof ByteCountComparison) {
                final ByteCountComparison byteRsult = (ByteCountComparison) item;
                formattedValue1 = fixLine(byteRsult.getValue1() == null ? null : fileSizeFormatter.format(byteRsult.getValue1()));
                formattedValue2 = fixLine(byteRsult.getValue2() == null ? null : fileSizeFormatter.format(byteRsult.getValue2()));
            } else {
                formattedValue1 = fixLine(item.getValue1AsString());
                formattedValue2 = fixLine(item.getValue2AsString());
            }

            final TableRow tableRow = TableRow.createRowFromCells(
                    createCellsFromData(comparedAttribute, item.getSimilarity(), formattedValue1, formattedValue2));

            tableBuilder.addRow(tableRow);
        }

        return tableBuilder.build();
    }

    private String fixLine(@Nullable final String value) {
        return value == null ? MISSING_VALUE : value;
    }

    @Nonnull
    private String getComparedAttribute(final Comparison item) {
        return item.getComparedAttribute() == null
                ? ""
                : item.getComparedAttribute();
    }

    private List<Cell> createCellsFromData(final String comparedAttribute,
                                           final Similarity similarity,
                                           final String value1,
                                           final String value2) {

        final List<Cell> cells = new ArrayList<>();
        cells.add(new StringCell(comparedAttribute));
        cells.add(new StringCell(
                TableCellIdResolver.getStatusString(similarity),
                TableCellIdResolver.getIdForSimilarity(similarity)));
        cells.add(new StringCell(value1, MISSING_VALUE.equals(value1) ? "missing" : null));
        cells.add(new StringCell(value2, MISSING_VALUE.equals(value2) ? "missing" : null));

        return cells;
    }
}
