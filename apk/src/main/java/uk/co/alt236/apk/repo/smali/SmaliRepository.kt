package uk.co.alt236.apk.repo.smali

import org.jf.baksmali.Adaptors.ClassDefinition
import org.jf.baksmali.BaksmaliOptions
import org.jf.dexlib2.DexFileFactory
import org.jf.dexlib2.Opcodes
import org.jf.dexlib2.iface.ClassDef
import org.jf.dexlib2.iface.DexFile
import org.jf.util.IndentingWriter
import uk.co.alt236.apk.repo.dex.DexRepository
import uk.co.alt236.apk.repo.dex.model.DexClassType
import java.io.File
import java.io.StringWriter
import java.util.*
import javax.annotation.Nullable

class SmaliRepository(private val file: File,
                      private val dexRepository: DexRepository) {
    private val baksmaliOptions: BaksmaliOptions = BaksmaliOptions()
    private val classDefinitions: Map<String, ClassDef> by lazy {
        val map = HashMap<String, ClassDef>()

        for (name in dexRepository.dexFileNames) {
            val dexFile = readDex(file, name)
            for (classDef in dexFile.classes) {
                map[classDef.type] = classDef
            }
        }
        map
    }

    @Nullable
    fun getSmaliForType(classType: DexClassType): String? {
        return disassemble(classDefinitions[classType.type])
    }

    private fun disassemble(classDef: ClassDef?): String? {
        if (classDef == null) {
            return null
        }

        val definition = ClassDefinition(baksmaliOptions, classDef)
        val stringWriter = StringWriter()

        definition.writeTo(IndentingWriter(stringWriter))

        return stringWriter.buffer.toString()
    }


    private fun readDex(apk: File, name: String): DexFile {
        return DexFileFactory.loadDexEntry(apk, name, true, Opcodes.getDefault())
    }
}
