package uk.co.alt236.apk.repo.dex.model

import org.jf.dexlib2.iface.ClassDef
import java.util.regex.Pattern

data class DexClassType(val type: String) {
    constructor(classDef: ClassDef) : this(classDef.type)

    val isInnerClass: Boolean by lazy { INNER_CLASS_PATTERN.matcher(type).matches() }

    val isLambda: Boolean by lazy { LAMBDA_CLASS_PATTERN.matcher(type).matches() }

    val classSimpleName: String by lazy {
        val lastSlash = type.lastIndexOf('/')

        val name = if (lastSlash == -1) {
            type
        } else {
            type.substring(lastSlash + 1, type.length - 1)
        }

        if (name.endsWith(";")) {
            name.substring(0, name.length - 2)
        } else {
            name
        }
    }

    val packageName: String by lazy {
        val lastSlash = type.lastIndexOf('/')

        val path = if (lastSlash == -1) {
            ""
        } else {
            type.substring(0, lastSlash)
        }

        val packagePath = path.replace("/".toRegex(), ".")
        if (packagePath.startsWith("L")) {
            packagePath.substring(1)
        } else {
            path
        }
    }

    override fun toString(): String {
        return type
    }

    companion object {
        private const val INNER_CLASS_REGEX = ".*\\$[0-9]+;$"
        private const val LAMBDA_CLASS_REGEX = ".*\\$\\\$Lambda\\$[0-9]+;$"
        private val INNER_CLASS_PATTERN = Pattern.compile(INNER_CLASS_REGEX)
        private val LAMBDA_CLASS_PATTERN = Pattern.compile(LAMBDA_CLASS_REGEX)
    }
}
