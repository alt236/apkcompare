package dex;

import org.junit.Assert;
import org.junit.Test;
import uk.co.alt236.apk.repo.dex.DexClassTypeToString;
import uk.co.alt236.apk.repo.dex.model.DexClassType;

import static org.junit.Assert.assertEquals;

public class DexClassTypeToStringTest {

    @Test
    public void convertsVoidsProperly() {
        final String typeString = "V";
        final String typeName = "void";

        final DexClassType classType = new DexClassType(typeString);
        Assert.assertEquals(typeName, DexClassTypeToString.toString(classType));
    }

    @Test
    public void convertsBoolProperly() {
        final String typeString = "Z";
        final String typeName = "bool";

        final DexClassType classType = new DexClassType(typeString);
        assertEquals(typeName, DexClassTypeToString.toString(classType));
    }

    @Test
    public void convertsByteProperly() {
        final String typeString = "B";
        final String typeName = "byte";

        final DexClassType classType = new DexClassType(typeString);
        assertEquals(typeName, DexClassTypeToString.toString(classType));
    }


    @Test
    public void convertsObjectProperly() {
        final String typeString = "Ljava/lang/Throwable;";
        final String typeName = "Ljava/lang/Throwable;";

        final DexClassType classType = new DexClassType(typeString);
        assertEquals(typeName, DexClassTypeToString.toString(classType));
    }

    @Test
    public void convertsIntProperly() {
        final String typeString = "I";
        final String typeName = "int";

        final DexClassType classType = new DexClassType(typeString);
        assertEquals(typeName, DexClassTypeToString.toString(classType));
    }

    @Test
    public void convertsLongProperly() {
        final String typeString = "J";
        final String typeName = "long";

        final DexClassType classType = new DexClassType(typeString);
        assertEquals(typeName, DexClassTypeToString.toString(classType));
    }

    @Test
    public void convertsShortProperly() {
        final String typeString = "S";
        final String typeName = "short";

        final DexClassType classType = new DexClassType(typeString);
        assertEquals(typeName, DexClassTypeToString.toString(classType));
    }

    @Test
    public void convertsFloatProperly() {
        final String typeString = "F";
        final String typeName = "float";

        final DexClassType classType = new DexClassType(typeString);
        assertEquals(typeName, DexClassTypeToString.toString(classType));
    }

    @Test
    public void convertsCharProperly() {
        final String typeString = "C";
        final String typeName = "char";

        final DexClassType classType = new DexClassType(typeString);
        assertEquals(typeName, DexClassTypeToString.toString(classType));
    }

    @Test
    public void convertsDoubleProperly() {
        final String typeString = "D";
        final String typeName = "double";

        final DexClassType classType = new DexClassType(typeString);
        assertEquals(typeName, DexClassTypeToString.toString(classType));
    }

    @Test
    public void convertsInitProperly() {
        final String typeString = "<init>";
        final String typeName = "<init>";

        final DexClassType classType = new DexClassType(typeString);
        assertEquals(typeName, DexClassTypeToString.toString(classType));
    }

    @Test
    public void convertsClinitProperly() {
        final String typeString = "<clinit>";
        final String typeName = "<clinit>";

        final DexClassType classType = new DexClassType(typeString);
        assertEquals(typeName, DexClassTypeToString.toString(classType));
    }

    @Test
    public void convertOneDimensionBoolArray() {
        final String typeString = "[Z";
        final String typeName = "bool[]";

        final DexClassType classType = new DexClassType(typeString);
        assertEquals(typeName, DexClassTypeToString.toString(classType));
    }

    @Test
    public void convertTwoDimensionBoolArray() {
        final String typeString = "[[Z";
        final String typeName = "bool[][]";

        final DexClassType classType = new DexClassType(typeString);
        assertEquals(typeName, DexClassTypeToString.toString(classType));
    }


    @Test
    public void convertOneDimensionObjectArray() {
        final String typeString = "[Ljava/lang/Throwable;";
        final String typeName = "Ljava/lang/Throwable;[]";

        final DexClassType classType = new DexClassType(typeString);
        assertEquals(typeName, DexClassTypeToString.toString(classType));
    }

    @Test
    public void convertTwoDimensionObjectArray() {
        final String typeString = "[[Ljava/lang/Throwable;";
        final String typeName = "Ljava/lang/Throwable;[][]";

        final DexClassType classType = new DexClassType(typeString);
        assertEquals(typeName, DexClassTypeToString.toString(classType));
    }


}