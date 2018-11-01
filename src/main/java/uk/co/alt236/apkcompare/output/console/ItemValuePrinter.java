package uk.co.alt236.apkcompare.output.console;

import uk.co.alt236.apkcompare.comparators.results.Similarity;
import uk.co.alt236.apkcompare.comparators.results.comparisons.ByteCountComparison;
import uk.co.alt236.apkcompare.comparators.results.comparisons.Comparison;
import uk.co.alt236.apkcompare.output.logging.Logger;
import uk.co.alt236.apkcompare.util.Colorizer;
import uk.co.alt236.apkcompare.util.FileSizeFormatter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

class ItemValuePrinter {
    private static final String MISSING_VALUE = "*** MISSING ***";

    private final FileSizeFormatter fileSizeFormatter;
    private final String colouredMissingValue;
    private final IndentGiver indentGiver;
    private final Colorizer colorizer;

    public ItemValuePrinter(FileSizeFormatter fileSizeFormatter,
                            Colorizer colorizer,
                            final IndentGiver indentGiver) {
        this.indentGiver = indentGiver;
        this.colorizer = colorizer;
        this.fileSizeFormatter = fileSizeFormatter;
        this.colouredMissingValue = colorizer.important(MISSING_VALUE);
    }

    public void printItemValues(final Comparison item,
                                final int indentLevel,
                                final boolean colourDifferences) {

        final String formattedValue1;
        final String formattedValue2;

        final String comparedAttribute = getComparedAttribute(item, colourDifferences);

        if (item instanceof ByteCountComparison) {
            final ByteCountComparison byteRsult = (ByteCountComparison) item;
            formattedValue1 = combineLine(comparedAttribute, byteRsult.getValue1() == null ? null : fileSizeFormatter.format(byteRsult.getValue1()));
            formattedValue2 = combineLine(comparedAttribute, byteRsult.getValue2() == null ? null : fileSizeFormatter.format(byteRsult.getValue2()));
        } else {
            formattedValue1 = combineLine(comparedAttribute, item.getValue1AsString());
            formattedValue2 = combineLine(comparedAttribute, item.getValue2AsString());
        }

        final String indent = indentGiver.getIndentation(indentLevel);
        Logger.get().out(indent + "APK 1: " + formattedValue1);
        Logger.get().out(indent + "APK 2: " + formattedValue2);
    }

    private String combineLine(@Nonnull final String comparedAttribute,
                               @Nullable final String value) {

        return comparedAttribute + (value == null ? colouredMissingValue : value);

    }

    @Nonnull
    private String getComparedAttribute(final Comparison item, final boolean colorise) {
        final String comparedAttribute = item.getComparedAttribute() == null
                ? ""
                : item.getComparedAttribute() + ": ";

        if (comparedAttribute.isEmpty()) {
            return comparedAttribute;
        } else {
            if (colorise) {
                if (item.getSimilarity() == Similarity.IDENTICAL) {
                    return comparedAttribute;
                } else {
                    return colorizer.error(comparedAttribute);
                }
            } else {
                return comparedAttribute;
            }
        }
    }
}
