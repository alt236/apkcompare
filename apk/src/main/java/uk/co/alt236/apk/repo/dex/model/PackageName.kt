package uk.co.alt236.apk.repo.dex.model

import java.util.*

data class PackageName(private val packageName: String) {
    private val packageNameParts: Array<String> = packageName.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

    val parent: PackageName by lazy {
        if (packageNameParts.isNotEmpty()) {
            val newArray = Arrays.copyOfRange(packageNameParts, 0, packageNameParts.size - 1)
            toPackageName(*newArray)
        } else {
            PackageName("")
        }
    }

    val parents: List<PackageName>  by lazy {
        val retVal = ArrayList<PackageName>()

        for (i in packageNameParts.indices) {
            val newArray = Arrays.copyOfRange(packageNameParts, 0, i)
            val newPackageName = toPackageName(*newArray)
            retVal.add(newPackageName)
        }
        retVal
    }

    val leaf: PackageName  by lazy {
        if (packageNameParts.isNotEmpty()) {
            PackageName(packageNameParts[packageNameParts.size - 1])
        } else {
            PackageName("")
        }
    }

    val root: PackageName  by lazy {
        if (packageNameParts.isNotEmpty()) {
            PackageName(packageNameParts[0])
        } else {
            PackageName("")
        }
    }

    override fun toString(): String {
        return packageName
    }

    private fun toPackageName(vararg parts: String): PackageName {
        val sb = StringBuilder()
        for (part in parts) {
            if (sb.length > 0) {
                sb.append('.')
            }
            sb.append(part)
        }
        return PackageName(sb.toString())
    }
}
