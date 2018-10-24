package uk.co.alt236.apkcompare.repo.dex;

import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import uk.co.alt236.apkcompare.repo.dex.model.DexClass;
import uk.co.alt236.apkcompare.repo.dex.model.DexFile;
import uk.co.alt236.apkcompare.util.StreamUtils;
import uk.co.alt236.apkcompare.zip.common.Entry;
import uk.co.alt236.apkcompare.zip.common.ZipContents;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class DexRepository {
    private final ZipContents zipContents;
    private final List<DexFile> dexFiles;
    private final Map<String, DexClass> classMap;

    public DexRepository(final ZipContents zipContents) {
        this.zipContents = zipContents;
        this.dexFiles = new ArrayList<>();
        this.classMap = new HashMap<>();
    }

    public List<DexFile> getDexFiles() {
        loadDexFiles();
        return Collections.unmodifiableList(dexFiles);
    }

    public Set<DexClass> getAllClasses() {
        loadDexFiles();

        synchronized (this) {
            if (classMap.isEmpty()) {
                dexFiles.stream()
                        .flatMap(dexFile -> dexFile.getClasses().stream())
                        .forEach(dexClass -> classMap.put(dexClass.getType(), dexClass));
            }
        }

        return new HashSet<>(classMap.values());
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

    private List<Entry> getDexFileEntries() {
        return zipContents
                .getEntries(entry -> !entry.isDirectory() && entry.getName().toLowerCase(Locale.US).endsWith(".dex"));
    }

    @Nullable
    public DexClass getClassByType(String classType) {
        loadDexFiles();

        return classMap.get(classType);
    }
}
