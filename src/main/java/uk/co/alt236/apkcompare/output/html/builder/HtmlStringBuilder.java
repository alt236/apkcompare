package uk.co.alt236.apkcompare.output.html.builder;

import org.apache.commons.lang.StringEscapeUtils;

class HtmlStringBuilder {
    private final StringBuilder sb;

    HtmlStringBuilder() {
        sb = new StringBuilder();
    }

    void append(final String value) {
        sb.append(value);
    }

    void append(final Object value) {
        sb.append(value);
    }

    void appendNewLine() {
        sb.append('\n');
    }

    void appendEscapableText(final String val) {
        sb.append(StringEscapeUtils.escapeHtml(val));
    }

    @Override
    public String toString() {
        return sb.toString();
    }

}