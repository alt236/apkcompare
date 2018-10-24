package uk.co.alt236.apkcompare.comparators;

import javax.annotation.Nonnull;

public class CustomResultItem implements ResultItem {

    private final String title;
    private final String value1;
    private final String value2;
    private final Similarity similarity;

    public CustomResultItem(String title,
                            String value1,
                            String value2,
                            @Nonnull Similarity similarity) {
        this.title = title;
        this.value1 = value1;
        this.value2 = value2;
        this.similarity = similarity;
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
        return similarity;
    }
}
