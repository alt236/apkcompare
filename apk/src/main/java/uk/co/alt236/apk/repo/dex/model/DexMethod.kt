package uk.co.alt236.apk.repo.dex.model

import org.jf.dexlib2.dexbacked.DexBackedMethod
import uk.co.alt236.apk.util.ImmutableCollectors

data class DexMethod internal constructor(val accessFlags: Int,
                                          val returnType: DexClassType,
                                          val name: String,
                                          val parameters: List<Parameter>) {

    companion object {
        @JvmStatic
        fun create(method: DexBackedMethod): DexMethod {
            val parameters = method.parameters
                    .stream()
                    .map<Parameter> { Parameter(it) }
                    .collect(ImmutableCollectors.toImmutableList())

            return DexMethod(
                    accessFlags = method.getAccessFlags(),
                    returnType = DexClassType(method.returnType),
                    name = method.name,
                    parameters = parameters)
        }
    }
}
