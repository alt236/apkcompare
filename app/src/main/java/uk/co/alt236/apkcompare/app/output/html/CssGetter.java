package uk.co.alt236.apkcompare.app.output.html;

import java.util.Scanner;

class CssGetter {
    private static final String SOURCE = "https://www.w3schools.com/css/css_table.asp";

    CssGetter() {
    }

    String getStyleSheet() {
        return new Scanner(getClass().getClassLoader().getResourceAsStream("html_table_css.css"), "UTF-8")
                .useDelimiter("\\A")
                .next();
    }
}