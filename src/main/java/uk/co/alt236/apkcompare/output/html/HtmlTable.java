package uk.co.alt236.apkcompare.output.html;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class HtmlTable {

    private final int numberOfColumns;
    private final List<List<String>> lines;

    private HtmlTable(Builder builder) {
        numberOfColumns = builder.numberOfColumns;
        lines = builder.lines;
    }


    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public List<List<String>> getLines() {
        return lines;
    }

    public static final class Builder {
        private final int numberOfColumns;
        private List<List<String>> lines = new ArrayList<>();

        public Builder(final int numberOfColumns) {
            this.numberOfColumns = numberOfColumns;
        }

        @Nonnull
        public Builder addLine(@Nonnull List<String> line) {
            lines.add(line);
            return this;
        }

        @Nonnull
        public HtmlTable build() {
            for (final List<String> line : lines) {
                if (line.size() != numberOfColumns) {
                    throw new IllegalStateException("Expected length of " + numberOfColumns + ", got " + line.size() + ": " + line);
                }
            }
            return new HtmlTable(this);
        }
    }
}
