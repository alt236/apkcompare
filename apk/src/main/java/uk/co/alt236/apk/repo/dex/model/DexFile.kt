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
            return DexFile(dexBackedFile.methodSection.size.toLong(),
                    dexBackedFile.classSection.size.toLong(),
                    dexBackedFile.fieldSection.size.toLong(),
                    dexBackedFile.stringSection.size.toLong(),
                    dexBackedFile.protoSection.size.toLong(),
                    DexClass.getClasses(name, dexBackedFile.classes),
                    fileSize,
                    name)
        }
    }
}
