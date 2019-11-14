package uk.co.alt236.apk.repo.dex.model

import org.jf.dexlib2.dexbacked.DexBackedDexFile

data class DexFile internal constructor(val methodCount: Long,
                                        val classCount: Long,
                                        val fieldCount: Long,
                                        val stringCount: Long,
                                        val protoCount: Long,
                                        val classes: Set<DexClass>,
                                        val fileSize: Long,
                                        val name: String) {
    companion object {

        fun create(dexBackedFile: DexBackedDexFile,
                   name: String,
                   fileSize: Long): DexFile {
            return DexFile(dexBackedFile.methodCount.toLong(),
                    dexBackedFile.classCount.toLong(),
                    dexBackedFile.fieldCount.toLong(),
                    dexBackedFile.stringCount.toLong(),
                    dexBackedFile.protoCount.toLong(),
                    DexClass.getClasses(name, dexBackedFile.classes),
                    fileSize,
                    name)
        }
    }
}
