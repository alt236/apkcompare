package uk.co.alt236.apkcompare.repo.dex.model;

import org.jf.dexlib2.iface.ClassDef;

import java.util.Objects;
import java.util.regex.Pattern;

public class DexClassType {
    private static final String INNER_CLASS_REGEX = ".*\\$[0-9]+;$";
    private static final String LAMBDA_CLASS_REGEX = ".*\\$\\$Lambda\\$[0-9]+;$";
    private static final Pattern INNER_CLASS_PATTERN = Pattern.compile(INNER_CLASS_REGEX);
    private static final Pattern LAMBDA_CLASS_PATTERN = Pattern.compile(LAMBDA_CLASS_REGEX);

    private final String classType;

    public DexClassType(final ClassDef classDef) {
        this(classDef.getType());
    }

    public DexClassType(final String classType) {
        this.classType = classType;
    }

    public String getType() {
        return classType;
    }

    public boolean isInnerClass() {
        return INNER_CLASS_PATTERN.matcher(classType).matches();
    }

    public boolean isLambda() {
        return LAMBDA_CLASS_PATTERN.matcher(classType).matches();
    }


    public String getClassSimpleName() {
        final int lastSlash = classType.lastIndexOf('/');

        final String name;
        if (lastSlash == -1) {
            name = classType;
        } else {
            name = classType.substring(lastSlash + 1, classType.length() - 1);
        }

        return name.endsWith(";") ? name.substring(0, name.length() - 2) : name;
    }

    public String getPackageName() {
        final int lastSlash = classType.lastIndexOf('/');

        final String path;
        if (lastSlash == -1) {
            path = "";
        } else {
            path = classType.substring(0, lastSlash);
        }

        final String packagePath = path.replaceAll("/", ".");
        return packagePath.startsWith("L") ? packagePath.substring(1) : path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DexClassType that = (DexClassType) o;
        return Objects.equals(classType, that.classType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classType);
    }

    @Override
    public String toString() {
        return classType;
    }
}
