package uk.co.alt236.apkcompare.output.html;

import java.util.List;

class HtmlBuilder {
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

        sb.append("<table>");
        sb.append("\n");
        for (final List<String> line : table.getLines()) {
            sb.append("<tr>");

            for (final String column : line) {
                sb.append("<td>");
                sb.append(column);
                sb.append("</td>");
            }

            sb.append("</tr>");
            sb.append("\n");

        }
        sb.append("</table>");
        sb.append("\n");
        sb.append("\n");
        document.append(sb);
    }
}
