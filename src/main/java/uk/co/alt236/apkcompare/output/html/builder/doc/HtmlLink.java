package uk.co.alt236.apkcompare.output.html.builder.doc;

import org.apache.commons.lang.StringEscapeUtils;

import javax.annotation.Nullable;
import java.util.Locale;

public class HtmlLink {
    private static final String TEMPLATE_NO_LABEL = "<a href=\"%s\"/>";
    private static final String TEMPLATE_WITH_LABEL = "<a href=\"%s\">%s</a>";
    private final String label;
    private final String link;

    public HtmlLink(String link,
                    @Nullable String label) {
        this.link = link;
        this.label = label;
    }

    public String getLink() {
        return link;
    }

    @Nullable
    public String getLabel() {
        return StringEscapeUtils.escapeHtml(label);
    }

    public String getHtml() {

        if (label != null && label.length() > 0) {
            return String.format(Locale.US, TEMPLATE_WITH_LABEL, link, getLabel());
        } else {
            return String.format(Locale.US, TEMPLATE_NO_LABEL, link);
        }

    }
}
