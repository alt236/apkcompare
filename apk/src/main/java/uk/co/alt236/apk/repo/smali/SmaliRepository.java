package uk.co.alt236.apk.repo.smali;

import org.jf.baksmali.Adaptors.ClassDefinition;
import org.jf.baksmali.BaksmaliOptions;
import org.jf.dexlib2.DexFileFactory;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.DexFile;
import org.jf.util.IndentingWriter;
import uk.co.alt236.apk.repo.dex.DexRepository;
import uk.co.alt236.apk.repo.dex.model.DexClassType;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class SmaliRepository {
    private final Map<String, ClassDef> classDefinitions;
    private final BaksmaliOptions baksmaliOptions;
    private final DexRepository dexRepository;
    private final File file;

    public SmaliRepository(final File file,
                           final DexRepository dexRepository) {

        this.baksmaliOptions = new BaksmaliOptions();
        this.classDefinitions = new HashMap<>();
        this.file = file;
        this.dexRepository = dexRepository;
    }

    @Nullable
    public String getSmaliForType(final DexClassType classType) {
        loadDexFiles();
        return disassemble(classDefinitions.get(classType.getType()));
    }


    @Nullable
    private String disassemble(@Nullable ClassDef classDef) {
        if (classDef == null) {
            return null;
        }

        final ClassDefinition definition = new ClassDefinition(baksmaliOptions, classDef);
        final StringWriter stringWriter = new StringWriter();

        try {
            definition.writeTo(new IndentingWriter(stringWriter));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return stringWriter.getBuffer().toString();
    }


    private synchronized void loadDexFiles() {
        if (!classDefinitions.isEmpty()) {
            return;
        }

        for (final String name : dexRepository.getDexFileNames()) {
            final DexFile dexFile = readDex(file, name);
            for (final ClassDef classDef : dexFile.getClasses()) {
                classDefinitions.put(classDef.getType(), classDef);
            }
        }
    }

    private DexFile readDex(final File apk, final String name) {
        try {
            return DexFileFactory.loadDexEntry(apk, name, true, Opcodes.getDefault());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
