package uk.co.alt236.apkcompare.output.console;

class IndentGiver {
    private static final String LEVEL_0_INDENT = "";
    private static final String LEVEL_1_INDENT = "\t";
    private static final String LEVEL_2_INDENT = "\t\t";
    private static final String LEVEL_3_INDENT = "\t\t\t";

    public String getIndentation(int indentLevel) {
        if (indentLevel == 0) {
            return LEVEL_0_INDENT;
        } else if (indentLevel == 1) {
            return LEVEL_1_INDENT;
        } else if (indentLevel == 2) {
            return LEVEL_2_INDENT;
        } else if (indentLevel == 3) {
            return LEVEL_3_INDENT;
        } else {
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < indentLevel; i++) {
                sb.append(LEVEL_1_INDENT);
            }
            return sb.toString();
        }
    }
}
