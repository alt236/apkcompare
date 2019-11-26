package uk.co.alt236.apkcompare.app.output.html;

import uk.co.alt236.apkcompare.app.comparators.results.Similarity;

class TableCellIdResolver {

    public static String getIdForSimilarity(Similarity similarity) {
        return similarity == Similarity.IDENTICAL ? null : "error";
    }


    public static String getStatusString(final Similarity similarity) {
        if (similarity == Similarity.IDENTICAL) {
            return "SAME";
        } else {
            return "DIFFERENT";
        }
    }

}
