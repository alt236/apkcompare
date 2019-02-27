package uk.co.alt236.apkcompare.app.output.html;

import java.util.Scanner;

public class CssGetter {
    private static final String SOURCE = "https://www.w3schools.com/css/css_table.asp";

    public CssGetter() {

    }

    public String getStyleSheet() {
        return new Scanner(getClass().getClassLoader().getResourceAsStream("html_table_css.css"), "UTF-8")
                .useDelimiter("\\A")
                .next();
    }
}