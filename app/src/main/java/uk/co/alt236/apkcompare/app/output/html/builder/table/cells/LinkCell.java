package uk.co.alt236.apkcompare.app.output.html.builder.table.cells;

import uk.co.alt236.apkcompare.app.output.html.builder.doc.HtmlLink;

public class LinkCell implements Cell {
    private final HtmlLink htmlLink;
    private final String id;

    public LinkCell(HtmlLink htmlLink) {
        this(htmlLink, null);
    }

    public LinkCell(HtmlLink htmlLink,
                    String id) {
        this.htmlLink = htmlLink;
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getContent() {
        return htmlLink.getHtml();
    }

    @Override
    public boolean isPreEscaped() {
        return true;
    }
}
