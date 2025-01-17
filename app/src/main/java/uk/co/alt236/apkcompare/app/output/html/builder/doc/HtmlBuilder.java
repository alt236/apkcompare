package uk.co.alt236.apkcompare.app.output.html.builder.doc;

import uk.co.alt236.apkcompare.app.output.html.builder.table.HtmlTable;
import uk.co.alt236.apkcompare.app.output.html.builder.table.TableRow;
import uk.co.alt236.apkcompare.app.output.html.builder.table.cells.Cell;

import java.util.Locale;

public class HtmlBuilder {
    private static final String TEMPLATE_TAG_WITH_ID = "<%s id='%s'>";
    private static final String TEMPLATE_TAG_WITHOUT_ID = "<%s>";

    private final HtmlStringBuilder document;
    private final boolean prettyHtml;

    public HtmlBuilder(final boolean prettyHtml) {
        this.document = new HtmlStringBuilder(prettyHtml);
        this.prettyHtml = prettyHtml;
    }

    public void startDocument() {
        document.append("<html>");
        document.appendNewLine();
    }

    public void endDocument() {
        document.append("</html>");
        document.appendNewLine();
    }

    public void addStyle(final String style) {
        document.append("<style>");
        document.appendNewLine();
        document.appendEscapableText(style);
        document.appendNewLine();
        document.append("</style>");
        document.appendNewLine();
    }

    @SuppressWarnings("StringConcatenationInsideStringBufferAppend")
    public void addHeader(final String header,
                          int level) {
        document.append("<h" + level + ">");
        document.appendEscapableText(header);
        document.append("</h" + level + ">");
        document.appendNewLine();
        document.appendNewLine();
    }

    public void addTitle(final String title) {
        document.append("<title>");
        document.appendEscapableText(title);
        document.append("</title>");
        document.appendNewLine();
        document.appendNewLine();
    }

    public String toString() {
        return document.toString();
    }

    public void addTable(HtmlTable table) {
        if (table.getNumberOfColumns() == 0
                || table.getLines().isEmpty()) {
            return;
        }

        final HtmlStringBuilder sb = new HtmlStringBuilder(prettyHtml);

        sb.append(getTagWithId("table", table.getId()));
        sb.appendNewLine();

        for (final TableRow row : table.getLines()) {
            sb.append("<tr>");

            for (final Cell cell : row.getCells()) {
                final String openingTag = row.isHeader()
                        ? getTagWithId("th", cell.getId())
                        : getTagWithId("td", cell.getId());
                final String closingTag = row.isHeader()
                        ? "</th>"
                        : "</td>";

                sb.append(openingTag);
                if (cell.isPreEscaped()) {
                    sb.append(cell.getContent());
                } else {
                    sb.appendEscapableText(cell.getContent());
                }
                sb.append(closingTag);
            }

            sb.append("</tr>");
            sb.appendNewLine();

        }
        sb.append("</table>");
        sb.appendNewLine();
        sb.appendNewLine();
        document.append(sb);
    }

    public void startHead() {
        document.append("<head>");
        document.appendNewLine();
    }

    public void endHead() {
        document.append("</head>");
        document.appendNewLine();
    }

    private String getTagWithId(final String tag, final String id) {
        if (id == null) {
            return String.format(Locale.US, TEMPLATE_TAG_WITHOUT_ID, tag);
        } else {
            return String.format(Locale.US, TEMPLATE_TAG_WITH_ID, tag, id);
        }
    }
}
