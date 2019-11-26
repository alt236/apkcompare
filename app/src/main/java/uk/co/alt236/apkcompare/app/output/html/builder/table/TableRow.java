package uk.co.alt236.apkcompare.app.output.html.builder.table;

import uk.co.alt236.apkcompare.app.output.html.builder.table.cells.Cell;
import uk.co.alt236.apkcompare.app.output.html.builder.table.cells.StringCell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("WeakerAccess")
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
            cells.add(new StringCell(item));
        }

        return createHeaderRowFromCells(cells);
    }

    public static TableRow createHeaderRowFromCells(List<Cell> items) {
        return createRow(items, true);
    }

    public static TableRow createRowFromStrings(String... items) {
        return createRowFromStrings(Arrays.asList(items));
    }

    public static TableRow createRowFromStrings(List<String> items) {
        final List<Cell> cells = new ArrayList<>();

        for (final String item : items) {
            cells.add(new StringCell(item));
        }

        return createRowFromCells(cells);
    }

    public static TableRow createRowFromCells(Cell... items) {
        return createRow(Arrays.asList(items), false);
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

}

