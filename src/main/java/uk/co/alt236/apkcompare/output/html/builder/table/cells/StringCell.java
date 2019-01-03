package uk.co.alt236.apkcompare.output.html.builder.table.cells;

public class StringCell implements Cell {
    private final String content;
    private final String id;

    public StringCell(String content) {
        this(content, null);
    }

    public StringCell(final String content,
                      final String id) {
        this.content = content;
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public boolean isPreEscaped() {
        return false;
    }
}
