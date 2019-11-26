package uk.co.alt236.apk.repo.dex.model

import org.jf.dexlib2.dexbacked.DexBackedMethod
import uk.co.alt236.apk.util.ImmutableCollectors

data class DexMethod internal constructor(val ownerClass: DexClassType,
                                          val name: String,
                                          val parameters: List<Parameter>,
                                          val returnType: DexClassType,
                                          val accessFlags: Int) {

    companion object {
        @JvmStatic
        fun create(ownerClass: DexClassType, method: DexBackedMethod): DexMethod {
            val parameters = method.parameters
                    .stream()
                    .map<Parameter> { Parameter(it) }
                    .collect(ImmutableCollectors.toImmutableList())

            return DexMethod(
                    ownerClass = ownerClass,
                    accessFlags = method.getAccessFlags(),
                    returnType = DexClassType(method.returnType),
                    name = method.name,
                    parameters = parameters)
        }
    }
}
