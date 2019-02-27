package uk.co.alt236.apk.repo.dex.model;

import org.jf.dexlib2.dexbacked.DexBackedClassDef;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DexClass {
    private final DexClassType dexClassType;
    private final boolean innerClass;
    private final boolean lambdaClass;
    private final String simpleName;
    private final String superType;
    private final String sourceFile;
    private final PackageName packageName;
    private final int size;
    private final long numberOfMethods;
    private final long numberOfFields;
    private final long numberOfAnnotations;
    private final long numberOfInterfaces;
    private final int accessFlags;
    private final List<DexMethod> methods;

    private DexClass(final DexBackedClassDef classDef) {
        this.dexClassType = new DexClassType(classDef);
        this.simpleName = dexClassType.getClassSimpleName();
        this.superType = classDef.getSuperclass();
        this.sourceFile = classDef.getSourceFile();
        this.innerClass = dexClassType.isInnerClass();
        this.lambdaClass = dexClassType.isLambda();
        this.packageName = new PackageName(dexClassType.getPackageName());
        this.size = classDef.getSize();
        this.accessFlags = classDef.getAccessFlags();
        this.numberOfMethods = count(classDef.getMethods());
        this.numberOfFields = count(classDef.getFields());
        this.numberOfInterfaces = count(classDef.getInterfaces());
        this.numberOfAnnotations = count(classDef.getAnnotations());
        this.methods = StreamSupport.stream(classDef.getMethods().spliterator(), false)
                .map(DexMethod::new).collect(Collectors.toList());
    }

    static Set<DexClass> getClasses(final Set<? extends DexBackedClassDef> classes) {
        final Set<DexClass> retVal = new HashSet<>(classes.size());

        for (final DexBackedClassDef classDef : classes) {
            retVal.add(new DexClass(classDef));
        }

        return Collections.unmodifiableSet(retVal);
    }

    public DexClassType getType() {
        return dexClassType;
    }

    public String getSuperType() {
        return superType;
    }

    public boolean isInnerClass() {
        return innerClass;
    }

    public boolean isLambda() {
        return lambdaClass;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public PackageName getPackageName() {
        return packageName;
    }

    public long getSize() {
        return size;
    }

    public long getNumberOfMethods() {
        return numberOfMethods;
    }

    public long getNumberOfFields() {
        return numberOfFields;
    }

    private static long count(final Iterable<?> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false).count();
    }

    public long getNumberOfAnnotations() {
        return numberOfAnnotations;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public int getAccessFlags() {
        return accessFlags;
    }

    public long getNumberOfInterfaces() {
        return numberOfInterfaces;
    }

    public List<DexMethod> getMethods() {
        return methods;
    }
}
