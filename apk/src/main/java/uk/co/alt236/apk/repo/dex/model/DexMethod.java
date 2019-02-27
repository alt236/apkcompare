package uk.co.alt236.apk.repo.dex.model;

import org.jf.dexlib2.dexbacked.DexBackedMethod;
import uk.co.alt236.apk.repo.dex.DexClassTypeToString;

import java.util.List;
import java.util.stream.Collectors;

public class DexMethod {
    private final int accessFlags;
    private final DexClassType returnType;
    private final String name;
    private final List<Parameter> parameters;

    DexMethod(DexBackedMethod method) {
        accessFlags = method.getAccessFlags();
        returnType = new DexClassType(method.getReturnType());
        name = method.getName();
        parameters = method.getParameters()
                .stream()
                .map(Parameter::new)
                .collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public int getAccessFlags() {
        return accessFlags;
    }

    public DexClassType getReturnType() {
        return returnType;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return "DexMethod{" +
                "accessFlags=" + accessFlags +
                ", returnType=" + DexClassTypeToString.toString(returnType) +
                ", name='" + name + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
