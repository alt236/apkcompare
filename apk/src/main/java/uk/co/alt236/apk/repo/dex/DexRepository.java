package uk.co.alt236.apk.repo.dex;

import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import uk.co.alt236.apk.repo.dex.model.DexClass;
import uk.co.alt236.apk.repo.dex.model.DexClassType;
import uk.co.alt236.apk.repo.dex.model.DexFile;
import uk.co.alt236.apk.util.StreamUtils;
import uk.co.alt236.apk.zip.Entry;
import uk.co.alt236.apk.zip.ZipContents;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class DexRepository {
    private final ZipContents zipContents;
    private final List<DexFile> dexFiles;
    private final Map<DexClassType, DexClass> classMap;
    private final Set<DexClass> dexClasses;

    public DexRepository(final ZipContents zipContents) {
        this.zipContents = zipContents;
        this.dexFiles = new ArrayList<>();
        this.classMap = new HashMap<>();
        this.dexClasses = new HashSet<>();
    }

    public List<DexFile> getDexFiles() {
        loadDexFiles();
        return Collections.unmodifiableList(dexFiles);
    }

    public Set<DexClass> getAllClasses() {
        loadDexClasses();

        return Collections.unmodifiableSet(dexClasses);
    }

    public long getTotalMethodCount() {
        loadDexFiles();
        return dexFiles.stream().mapToLong(DexFile::getMethodCount).sum();
    }

    public long getTotalClassCount() {
        loadDexFiles();
        return dexFiles.stream().mapToLong(DexFile::getClassCount).sum();
    }

    public long getTotalFieldCount() {
        loadDexFiles();
        return dexFiles.stream().mapToLong(DexFile::getFieldCount).sum();
    }

    public long getAnonymousClassCount() {
        loadDexFiles();
        long count = 0;
        for (final DexFile dexFile : dexFiles) {
            count += dexFile.getClasses().stream().filter(DexClass::isInnerClass).count();
        }

        return count;
    }

    public long getLambdaClassCount() {
        loadDexFiles();
        long count = 0;
        for (final DexFile dexFile : dexFiles) {
            count += dexFile.getClasses().stream().filter(DexClass::isLambda).count();
        }

        return count;
    }

    public long getTotalStringCount() {
        loadDexFiles();
        return dexFiles.stream().mapToLong(DexFile::getStringCount).sum();
    }

    public long getTotalProtoCount() {
        loadDexFiles();
        return dexFiles.stream().mapToLong(DexFile::getProtoCount).sum();
    }

    @Nullable
    public DexClass getClassByType(DexClassType classType) {
        loadDexClasses();

        return classMap.get(classType);
    }

    private synchronized void loadDexFiles() {
        if (!dexFiles.isEmpty()) {
            return;
        }

        final List<Entry> entries = getDexFileEntries();
        for (final Entry entry : entries) {
            final InputStream is = zipContents.getInputStream(entry);
            try {
                final DexBackedDexFile dexFile = DexBackedDexFile.fromInputStream(Opcodes.getDefault(), is);
                dexFiles.add(new DexFile(dexFile, entry.getName(), entry.getFileSize()));
            } catch (final IOException e) {
                e.printStackTrace();
            }
            StreamUtils.close(is);
        }
    }

    private synchronized void loadDexClasses() {
        loadDexFiles();

        if (!classMap.isEmpty()) {
            return;
        }

        dexFiles.stream()
                .flatMap(dexFile -> dexFile.getClasses().stream())
                .forEach(dexClass -> classMap.put(dexClass.getType(), dexClass));
        dexClasses.addAll(new HashSet<>(classMap.values()));
    }

    private List<Entry> getDexFileEntries() {
        return zipContents
                .getEntries(entry -> !entry.isDirectory() && entry.getName().toLowerCase(Locale.US).endsWith(".dex"));
    }

    public List<String> getDexFileNames() {
        return getDexFiles().stream().map(DexFile::getName).collect(Collectors.toList());
    }
}
