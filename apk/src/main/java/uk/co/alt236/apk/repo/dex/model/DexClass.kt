package uk.co.alt236.apk.repo.dex.model

import org.jf.dexlib2.dexbacked.DexBackedClassDef
import uk.co.alt236.apk.util.ImmutableCollectors
import java.util.*
import java.util.stream.StreamSupport

data class DexClass internal constructor(val dexFileName: String,
                                         val type: DexClassType,
                                         val isInnerClass: Boolean,
                                         val isLambda: Boolean,
                                         val simpleName: String,
                                         val superType: String?,
                                         val sourceFile: String?,
                                         val packageName: PackageName,
                                         val size: Long,
                                         val numberOfMethods: Long,
                                         val numberOfFields: Long,
                                         val numberOfAnnotations: Long,
                                         val numberOfInterfaces: Long,
                                         val accessFlags: Int,
                                         val methods: List<DexMethod>) {

    companion object {

        @JvmStatic
        fun getClasses(dexFileName: String,
                       classes: Set<DexBackedClassDef>): Set<DexClass> {
            val retVal = HashSet<DexClass>(classes.size)

            for (classDef in classes) {
                retVal.add(create(dexFileName, classDef))
            }

            return Collections.unmodifiableSet(retVal)
        }

        private fun count(iterable: Iterable<*>): Long {
            return StreamSupport.stream(iterable.spliterator(), false).count()
        }

        private fun create(dexFileName: String,
                           classDef: DexBackedClassDef): DexClass {

            val dexClassType = DexClassType(classDef)
            return DexClass(
                    dexFileName = dexFileName,
                    type = dexClassType,
                    simpleName = dexClassType.classSimpleName,
                    superType = classDef.superclass,
                    sourceFile = classDef.sourceFile,
                    isInnerClass = dexClassType.isInnerClass,
                    isLambda = dexClassType.isLambda,
                    packageName = PackageName(dexClassType.packageName),
                    size = classDef.size.toLong(),
                    accessFlags = classDef.accessFlags,
                    numberOfMethods = count(classDef.methods),
                    numberOfFields = count(classDef.fields),
                    numberOfInterfaces = count(classDef.interfaces),
                    numberOfAnnotations = count(classDef.annotations),
                    methods = StreamSupport.stream(classDef.methods.spliterator(), false)
                            .map { DexMethod.create(dexClassType, it) }.collect(ImmutableCollectors.toImmutableList())
            )
        }
    }
}
