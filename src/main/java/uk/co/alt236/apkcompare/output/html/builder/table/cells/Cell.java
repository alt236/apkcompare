package uk.co.alt236.apkcompare.output.html.builder.table.cells;

import javax.annotation.Nullable;

public interface Cell {
    @Nullable
    String getId();

    String getContent();

    boolean isPreEscaped();
}
