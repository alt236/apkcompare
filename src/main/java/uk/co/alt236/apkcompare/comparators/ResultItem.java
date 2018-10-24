package uk.co.alt236.apkcompare.comparators;

import org.apache.commons.codec.binary.StringUtils;

public class ResultItem implements ComparisonResult {

    private final String title;
    private final String value1;
    private final String value2;

    public ResultItem(String title, String value1, String value2) {
        this.title = title;
        this.value1 = value1;
        this.value2 = value2;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public String getValue1() {
        return value1;
    }

    public String getValue2() {
        return value2;
    }

    @Override
    public Status getStatus() {
        if (StringUtils.equals(value1, value2)) {
            return Status.SAME;
        } else {
            return Status.DIFFERENT;
        }
    }
}
