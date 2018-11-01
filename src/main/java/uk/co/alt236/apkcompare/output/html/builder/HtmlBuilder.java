package uk.co.alt236.apkcompare.output.html.builder;

public class HtmlBuilder {
    private final StringBuilder document;

    public HtmlBuilder() {
        document = new StringBuilder();
    }

    public void startDocument() {
        document.append("<html>");
        document.append("\n");
    }

    public void endDocument() {
        document.append("</html>");
        document.append("\n");
    }

    public void addStyle(final String style) {
        document.append("<style>");
        document.append("\n");
        document.append(style);
        document.append("\n");
        document.append("</style>");
        document.append("\n");
    }

    @SuppressWarnings("StringConcatenationInsideStringBufferAppend")
    public void addHeader(final String header,
                          int level) {
        document.append("<h" + level + ">");
        document.append(header);
        document.append("</h" + level + ">");
        document.append("\n");
        document.append("\n");
    }

    public void addTitle(final String title) {
        document.append("<title>");
        document.append(title);
        document.append("</title>");
        document.append("\n");
        document.append("\n");
    }

    public String toString() {
        return document.toString();
    }

    public void addTable(HtmlTable table) {
        if (table.getNumberOfColumns() == 0
                || table.getLines().isEmpty()) {
            return;
        }

        final StringBuilder sb = new StringBuilder();

        if (table.getId() == null) {
            sb.append("<table>");
        } else {
            //noinspection StringConcatenationInsideStringBufferAppend
            sb.append("<table id='" + table.getId() + "'>");
        }

        sb.append("\n");
        for (final TableRow row : table.getLines()) {
            sb.append("<tr>");

            for (final TableRow.Cell cell : row.getCells()) {
                sb.append(row.isHeader() ? "<th>" : "<td>");
                sb.append(cell.getContent());
                sb.append(row.isHeader() ? "</th>" : "</td>");
            }

            sb.append("</tr>");
            sb.append("\n");

        }
        sb.append("</table>");
        sb.append("\n");
        sb.append("\n");
        document.append(sb);
    }

    public void startHead() {
        document.append("<head>");
        document.append("\n");
    }

    public void endHead() {
        document.append("</head>");
        document.append("\n");
    }
}
