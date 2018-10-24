package uk.co.alt236.apkcompare.comparators;

import org.apache.commons.codec.binary.StringUtils;

public class StringResultItem implements ResultItem {

    private final String title;
    private final String value1;
    private final String value2;

    public StringResultItem(String title,
                            String value1,
                            String value2) {
        this.title = title;
        this.value1 = value1;
        this.value2 = value2;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getValue1AsString() {
        return value1;
    }

    @Override
    public String getValue2AsString() {
        return value2;
    }

    @Override
    public Similarity getSimilarity() {
        if (StringUtils.equals(value1, value2)) {
            return Similarity.IDENTICAL;
        } else {
            return Similarity.DIFFERENT;
        }
    }
}
