package uk.co.alt236.apkcompare.repo.dex.model;

import org.jf.dexlib2.iface.MethodParameter;

public class Parameter {

    private final DexClassType type;
    private final String name;

    Parameter(MethodParameter param) {
        name = param.getName();
        type = new DexClassType(param.getType());
    }

    public DexClassType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
