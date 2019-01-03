package uk.co.alt236.apkcompare.output.html.builder.table;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class HtmlTable {

    private final int numberOfColumns;
    private final String id;
    private final List<TableRow> lines;

    private HtmlTable(Builder builder) {
        id = builder.id;
        numberOfColumns = builder.numberOfColumns;
        lines = builder.lines;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public List<TableRow> getLines() {
        return lines;
    }

    public String getId() {
        return id;
    }

    public static final class Builder {
        private final int numberOfColumns;
        private String id;
        private List<TableRow> lines = new ArrayList<>();

        public Builder(final int numberOfColumns) {
            this.numberOfColumns = numberOfColumns;
        }

        @Nonnull
        public Builder addRow(@Nonnull TableRow row) {
            lines.add(row);
            return this;
        }

        @Nonnull
        public Builder setId(@Nullable String id) {
            this.id = id;
            return this;
        }

        @Nonnull
        public HtmlTable build() {
            for (final TableRow line : lines) {
                if (line.getCells().size() != numberOfColumns) {
                    throw new IllegalStateException("Expected length of " + numberOfColumns + ", got " + line.getCells().size() + ": " + line);
                }
            }
            return new HtmlTable(this);
        }
    }
}
