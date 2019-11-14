package uk.co.alt236.apk.repo.dex.model

import org.jf.dexlib2.iface.MethodParameter

data class Parameter internal constructor(val name: String?, val type: DexClassType) {
    internal constructor(param: MethodParameter) : this(param.name, DexClassType(param.type))
}
