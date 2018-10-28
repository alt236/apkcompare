package uk.co.alt236.apkcompare.comparators.dex;

import org.jf.baksmali.Adaptors.ClassDefinition;
import org.jf.baksmali.BaksmaliOptions;
import org.jf.dexlib2.DexFileFactory;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.DexFile;
import org.jf.util.IndentingWriter;
import uk.co.alt236.apkcompare.apk.Apk;
import uk.co.alt236.apkcompare.comparators.results.ResultItem;
import uk.co.alt236.apkcompare.comparators.results.StringResultItem;
import uk.co.alt236.apkcompare.util.Hasher;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

class SmaliComparator {

    private final Apk apk1;
    private final Apk apk2;

    private final Hasher hasher;
    private final Map<String, ClassDef> apk1ClassDefinitions;
    private final Map<String, ClassDef> apk2ClassDefinitions;

    public SmaliComparator(final Hasher hasher,
                           final Apk apk1,
                           final Apk apk2) {
        this.apk1 = apk1;
        this.apk2 = apk2;

        this.hasher = hasher;
        this.apk1ClassDefinitions = new HashMap<>();
        this.apk2ClassDefinitions = new HashMap<>();
    }

    public ResultItem compareClasses(final String classType) {
        loadDexFiles();

        final ClassDef apk1ClassDef = apk1ClassDefinitions.get(classType);
        final ClassDef apk2ClassDef = apk2ClassDefinitions.get(classType);

        BaksmaliOptions options = new BaksmaliOptions();

        final String apk1ClassContents = disassemble(apk1ClassDef, options);
        final String apk2ClassContents = disassemble(apk2ClassDef, options);


        return new StringResultItem(
                classType,
                "SHA256",
                apk1ClassContents == null ? null : hasher.sha256Hex(apk1ClassContents.getBytes()),
                apk2ClassContents == null ? null : hasher.sha256Hex(apk2ClassContents.getBytes()));
    }

    @Nullable
    private String disassemble(@Nullable ClassDef classDef,
                               @Nonnull BaksmaliOptions options) {
        if (classDef == null) {
            return null;
        }

        final ClassDefinition definition = new ClassDefinition(options, classDef);
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
        if ((apk1ClassDefinitions.size() + apk2ClassDefinitions.size()) > 0) {
            return;
        }

        for (final String name : apk1.getDexFileNames()) {
            final DexFile dexFile = readDex(apk1, name);
            for (final ClassDef classDef : dexFile.getClasses()) {
                apk1ClassDefinitions.put(classDef.getType(), classDef);
            }
        }

        for (final String name : apk2.getDexFileNames()) {
            final DexFile dexFile = readDex(apk2, name);
            for (final ClassDef classDef : dexFile.getClasses()) {
                apk2ClassDefinitions.put(classDef.getType(), classDef);
            }
        }
        System.out.println("Loaded dex files!: " + apk1ClassDefinitions.size() + ",  " + apk2ClassDefinitions.size());
    }

    private DexFile readDex(final Apk apk, final String name) {
        try {
            return DexFileFactory.loadDexEntry(apk.getFile(), name, true, Opcodes.getDefault());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
