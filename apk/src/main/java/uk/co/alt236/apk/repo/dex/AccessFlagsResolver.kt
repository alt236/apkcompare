package uk.co.alt236.apk.repo.dex

import com.android.dx.rop.code.AccessFlags
import uk.co.alt236.apk.repo.dex.model.DexClass
import uk.co.alt236.apk.repo.dex.model.DexMethod

object AccessFlagsResolver {

    @JvmStatic
    fun resolve(item: DexClass): String {
        val result = if (item.isInnerClass) {
            AccessFlags.innerClassString(item.accessFlags)
        } else {
            AccessFlags.classString(item.accessFlags)
        }

        return sanitise(result)
    }

    @JvmStatic
    fun resolve(item: DexMethod): String {
        val result = AccessFlags.methodString(item.accessFlags)
        return sanitise(result)
    }

    private fun sanitise(flagString: String): String {
        return if ("0000" == flagString) {
            "none"
        } else {
            flagString
        }
    }
}
