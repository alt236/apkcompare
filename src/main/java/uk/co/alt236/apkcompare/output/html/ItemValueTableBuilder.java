package uk.co.alt236.apkcompare.output.html;

import uk.co.alt236.apkcompare.comparators.results.Similarity;
import uk.co.alt236.apkcompare.comparators.results.comparisons.ByteCountComparison;
import uk.co.alt236.apkcompare.comparators.results.comparisons.Comparison;
import uk.co.alt236.apkcompare.output.PrintabilityEvaluator;
import uk.co.alt236.apkcompare.output.html.builder.HtmlTable;
import uk.co.alt236.apkcompare.output.html.builder.TableRow;
import uk.co.alt236.apkcompare.util.FileSizeFormatter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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

    public HtmlTable createComparisonTable(final List<Comparison> comparisons,
                                           final boolean colourDifferences) {


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

            final String comparedAttribute = getComparedAttribute(item, colourDifferences);

            if (item instanceof ByteCountComparison) {
                final ByteCountComparison byteRsult = (ByteCountComparison) item;
                formattedValue1 = combineLine(byteRsult.getValue1() == null ? null : fileSizeFormatter.format(byteRsult.getValue1()));
                formattedValue2 = combineLine(byteRsult.getValue2() == null ? null : fileSizeFormatter.format(byteRsult.getValue2()));
            } else {
                formattedValue1 = combineLine(item.getValue1AsString());
                formattedValue2 = combineLine(item.getValue2AsString());
            }

            final TableRow tableRow = TableRow.createRowFromStrings(Arrays.asList(
                    comparedAttribute,
                    getStatusString(item),
                    formattedValue1,
                    formattedValue2));

            tableBuilder.addRow(tableRow);
        }

        return tableBuilder.build();
    }

    private String combineLine(@Nullable final String value) {
        return value == null ? MISSING_VALUE : value;
    }

    @Nonnull
    private String getComparedAttribute(final Comparison item, final boolean colorise) {
        final String comparedAttribute = item.getComparedAttribute() == null
                ? ""
                : item.getComparedAttribute();

        if (comparedAttribute.isEmpty()) {
            return comparedAttribute;
        } else {
//            if (colorise) {
//                if (item.getSimilarity() == Similarity.IDENTICAL) {
//                    return comparedAttribute;
//                } else {
//                    return colorizer.error(comparedAttribute);
//                }
//            } else {
            return comparedAttribute;
//            }
        }
    }

    private String getStatusString(final Comparison comparison) {
        if (comparison.getValue1AsString() == null || comparison.getValue2AsString() == null) {
            return "DIFFERENT";
        } else if (comparison.getSimilarity() == Similarity.IDENTICAL) {
            return "OK";
        } else {
            return "DIFFERENT";
        }
    }
}
