package uk.co.alt236.apk.repo.dex

import uk.co.alt236.apk.repo.dex.model.DexClassType

object DexClassTypeToString {

    // Source for the chars: https://github.com/JesusFreke/smali/wiki/TypesMethodsAndFields

    @JvmStatic
    fun toString(type: DexClassType): String {
        return toString(type.type)
    }

    private fun toString(typeString: String): String {
        val firstChar = typeString[0]

        return when (firstChar) {
            '<' -> typeString // Synthetic
            'L' -> typeString // Object
            'V' -> "void"
            'Z' -> "bool"
            'B' -> "byte"
            'S' -> "short"
            'C' -> "char"
            'I' -> "int"
            'J' -> "long"
            'F' -> "float"
            'D' -> "double"
            '[' -> parseArray(typeString)
            else -> throw IllegalArgumentException("Don't know how to parse '$typeString'")
        }
    }

    private fun parseArray(typeString: String): String {
        val lastBracket = typeString.lastIndexOf('[')
        val brackets = typeString.substring(0, lastBracket + 1)
        val type = typeString.substring(lastBracket + 1, typeString.length)

        val sb = StringBuilder()

        sb.append(toString(type))

        for (i in 0 until brackets.length) {
            sb.append("[]")
        }

        return sb.toString()
    }

}
