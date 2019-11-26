package uk.co.alt236.apkcompare.app.comparators.dex

import uk.co.alt236.apk.Apk
import uk.co.alt236.apk.repo.dex.AccessFlagsResolver
import uk.co.alt236.apk.repo.dex.model.DexClassType
import uk.co.alt236.apk.util.Hasher
import uk.co.alt236.apkcompare.app.comparators.results.ComparisonResult
import uk.co.alt236.apkcompare.app.comparators.results.comparisons.Comparison
import uk.co.alt236.apkcompare.app.comparators.results.comparisons.TypedComparison
import uk.co.alt236.apkcompare.app.comparators.results.groups.CompositeResult

internal class DexClassComparator(private val hasher: Hasher,
                                  private val apk1: Apk,
                                  private val apk2: Apk) {

    fun compareClasses(classType: DexClassType): ComparisonResult {

        val class1 = apk1.getClassByType(classType)
        val class2 = apk2.getClassByType(classType)

        val builder = CompositeResult.Builder()
        builder.title = "Overview"

        builder.withComparison(TypedComparison<String>(
                classType.type,
                "Sourcefile",
                class1?.sourceFile,
                class2?.sourceFile))

        builder.withComparison(TypedComparison<String>(
                classType.type,
                "Superclass",
                class1?.superType,
                class2?.superType))

        builder.withComparison(TypedComparison<String>(
                classType.type,
                "DEX File",
                class1?.dexFileName,
                class2?.dexFileName))

//        builder.withComparison(ByteCountComparison(
//                classType.type,
//                "Class Definition Size",
//                class1?.size,
//                class2?.size))

        builder.withComparison(TypedComparison<String>(
                classType.type,
                "Access Flags",
                if (class1 == null) null else AccessFlagsResolver.resolve(class1),
                if (class2 == null) null else AccessFlagsResolver.resolve(class2)))

        builder.withComparison(TypedComparison<Any>(
                classType.type,
                "Number of Annotations",
                class1?.numberOfAnnotations,
                class2?.numberOfAnnotations))

        builder.withComparison(TypedComparison<Any>(
                classType.type,
                "Number of Interfaces",
                class1?.numberOfInterfaces,
                class2?.numberOfInterfaces))

        builder.withComparison(TypedComparison<Any>(
                classType.type,
                "Number of Methods",
                class1?.numberOfMethods,
                class2?.numberOfMethods))

        builder.withComparison(TypedComparison<Any>(
                classType.type,
                "Number of Fields",
                class1?.numberOfFields,
                class2?.numberOfFields))

        builder.withComparisons(getSmaliComparisons(classType))

        return builder.build()
    }


    private fun getSmaliComparisons(classType: DexClassType): List<Comparison> {
        val apk1ClassContents = apk1.getSmaliForClassType(classType)
        val apk2ClassContents = apk2.getSmaliForClassType(classType)

        val comparison = TypedComparison<String>(
                classType.type,
                "Smali SHA256",
                if (apk1ClassContents == null) null else hasher.sha256Hex(apk1ClassContents),
                if (apk2ClassContents == null) null else hasher.sha256Hex(apk2ClassContents))

        return listOf(comparison)
    }
}