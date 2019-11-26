package uk.co.alt236.apkcompare.app.comparators.dex

import uk.co.alt236.apk.Apk
import uk.co.alt236.apk.repo.dex.AccessFlagsResolver
import uk.co.alt236.apk.repo.dex.DexClassTypeToString
import uk.co.alt236.apk.repo.dex.model.DexClassType
import uk.co.alt236.apk.repo.dex.model.DexMethod
import uk.co.alt236.apkcompare.app.comparators.results.ComparisonResult
import uk.co.alt236.apkcompare.app.comparators.results.comparisons.ClassTypeComparison
import uk.co.alt236.apkcompare.app.comparators.results.comparisons.TypedComparison
import uk.co.alt236.apkcompare.app.comparators.results.groups.CompositeResult
import kotlin.math.max

internal class DexMethodComparator(private val apk1: Apk,
                                   private val apk2: Apk) {

    fun compareClasses(classType: DexClassType): List<ComparisonResult> {
        val class1 = apk1.getClassByType(classType)
        val class2 = apk2.getClassByType(classType)
        val map1 = class1?.methods.createMapping()
        val map2 = class2?.methods.createMapping()
        val keys = map1.keys.union(map2.keys).toList().sorted()

        val comparisons = ArrayList<CompositeResult>()

        for (key in keys) {
            val builder = CompositeResult.Builder()
            builder.withTitle("Method: $key")
            val method1 = map1[key]
            val method2 = map2[key]

            builder.withComparison(TypedComparison<String>(
                    key,
                    "Method Signature",
                    method1?.constructFullSignature(),
                    method2?.constructFullSignature()))

            builder.withComparison(TypedComparison<String>(
                    key,
                    "Access flags",
                    method1?.getFriendlyAccessFlags(),
                    method2?.getFriendlyAccessFlags()))

            builder.withComparison(ClassTypeComparison(
                    key,
                    "Return type",
                    method1?.returnType,
                    method2?.returnType))

            builder.withComparison(TypedComparison<Int>(
                    key,
                    "Parameter count",
                    method1?.parameters?.size,
                    method2?.parameters?.size))

            appendParameters(builder, key, method1, method2)
            comparisons.add(builder.build())
        }

        return comparisons
    }

    private fun appendParameters(builder: CompositeResult.Builder,
                                 className: String,
                                 method1: DexMethod?,
                                 method2: DexMethod?) {

        val method1ParamCount = method1?.parameters?.size ?: 0
        val method2ParamCount = method2?.parameters?.size ?: 0
        val maxParams = max(method1ParamCount, method2ParamCount)

        for (i in 0 until maxParams) {
            val param1 = if (method1ParamCount > 0 && i <= method1ParamCount) method1?.parameters!![i] else null
            val param2 = if (method2ParamCount > 0 && i <= method2ParamCount) method2?.parameters!![i] else null

            builder.withComparison(ClassTypeComparison(
                    className,
                    "Param #${i + 1} Type",
                    param1?.type,
                    param2?.type))
        }

    }

    private fun List<DexMethod>?.createMapping(): Map<String, DexMethod> {

        if (this == null) {
            return emptyMap()
        }

        val result = HashMap<String, DexMethod>()
        for (method in this) {
            val name = method.constructUniqueSignature()
            if (result.containsKey(name)) {
                throwMethodExistsException(name, method, this)
            } else {
                result[name] = method
            }
        }

        return result
    }

    private fun DexMethod.constructUniqueSignature(): String {
        val sb = StringBuilder()

        //
        // Methods that are different only on return type are illegal in source but not in bytecode.
        // Thus we need to add this here to avoid exploding on dupes.
        //
        sb.append(DexClassTypeToString.toString(returnType))
        sb.append(" ")
        sb.append(name)
        sb.append("(")

        var count = 0
        for ((_, type) in parameters) {
            if (count > 0) {
                sb.append(", ")
            }
            sb.append(DexClassTypeToString.toString(type))
            count++
        }
        sb.append(")")

        return sb.toString()
    }

    private fun DexMethod.constructFullSignature(): String {
        val sb = StringBuilder()

        sb.append(AccessFlagsResolver.resolve(this))
        sb.append(" ")
        sb.append(DexClassTypeToString.toString(returnType))
        sb.append(" ")
        sb.append(name)
        sb.append("(")

        var count = 0
        for ((_, type) in parameters) {
            if (count > 0) {
                sb.append(", ")
            }
            sb.append(DexClassTypeToString.toString(type))
            count++
        }
        sb.append(")")

        return sb.toString()
    }

    private fun DexMethod?.getFriendlyAccessFlags(): String? {
        if (this == null) {
            return null
        }

        return AccessFlagsResolver.resolve(this)
    }

    private fun throwMethodExistsException(name: String,
                                           rawMethod: DexMethod,
                                           existingMethods: List<DexMethod>) {
        val sb = StringBuilder()
        sb.append("Duplicate method '$name'. Info: $rawMethod\n")
        sb.append("All methods:\n")
        for ((index, method) in existingMethods.withIndex()) {
            sb.append("$index: $method\n")
        }
        throw IllegalStateException(sb.toString())
    }
}