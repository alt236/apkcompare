package uk.co.alt236.apkcompare.output.html.builder;

import java.util.ArrayList;
import java.util.List;

public class TableRow {
    private final List<Cell> cells;
    private final boolean header;

    private TableRow(final List<Cell> cells,
                     final boolean header) {
        this.cells = cells;
        this.header = header;
    }

    public static TableRow createHeaderRowFromStrings(List<String> items) {
        final List<Cell> cells = new ArrayList<>();

        for (final String item : items) {
            cells.add(new Cell(item));
        }

        return createHeaderRowFromCells(cells);
    }

    public static TableRow createHeaderRowFromCells(List<Cell> items) {
        return createRow(items, true);
    }

    public static TableRow createRowFromStrings(List<String> items) {
        final List<Cell> cells = new ArrayList<>();

        for (final String item : items) {
            cells.add(new Cell(item));
        }

        return createRowFromCells(cells);
    }

    public static TableRow createRowFromCells(List<Cell> items) {
        return createRow(items, false);
    }

    private static TableRow createRow(final List<Cell> cells, final boolean header) {
        return new TableRow(cells, header);
    }

    public List<Cell> getCells() {
        return cells;
    }

    public boolean isHeader() {
        return header;
    }

    public static class Cell {
        private final String content;

        public Cell(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }
    }
}

