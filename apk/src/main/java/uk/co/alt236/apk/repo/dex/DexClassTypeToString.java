package uk.co.alt236.apk.repo.dex;

import uk.co.alt236.apk.repo.dex.model.DexClassType;

public final class DexClassTypeToString {

    // Source for the chars: https://github.com/JesusFreke/smali/wiki/TypesMethodsAndFields

    public static String toString(final DexClassType type) {
        return toString(type.getType());
    }

    private static String toString(final String typeString) {
        final char firstChar = typeString.charAt(0);

        final String retVal;

        switch (firstChar) {
            case '<': // Synthetic
                retVal = typeString;
                break;
            case 'L': // Object
                retVal = typeString;
                break;
            case 'V':
                retVal = "void";
                break;
            case 'Z':
                retVal = "bool";
                break;
            case 'B':
                retVal = "byte";
                break;
            case 'S':
                retVal = "short";
                break;
            case 'C':
                retVal = "char";
                break;
            case 'I':
                retVal = "int";
                break;
            case 'J':
                retVal = "long";
                break;
            case 'F':
                retVal = "float";
                break;
            case 'D':
                retVal = "double";
                break;
            case '[':
                retVal = parseArray(typeString);
                break;
            default:
                throw new IllegalArgumentException("Don't know how to parse " + typeString);
        }

        return retVal;
    }

    private static String parseArray(String typeString) {
        final int lastBracket = typeString.lastIndexOf('[');
        final String brackets = typeString.substring(0, lastBracket + 1);
        final String type = typeString.substring(lastBracket + 1, typeString.length());


        final StringBuilder sb = new StringBuilder();

        sb.append(toString(type));

        for (int i = 0; i < brackets.length(); i++) {
            sb.append("[]");
        }

        return sb.toString();
    }

}
