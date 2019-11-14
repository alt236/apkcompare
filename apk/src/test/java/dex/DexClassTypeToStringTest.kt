package dex

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import uk.co.alt236.apk.repo.dex.DexClassTypeToString
import uk.co.alt236.apk.repo.dex.model.DexClassType

class DexClassTypeToStringTest {

    @Test
    fun convertsVoidsProperly() {
        val typeString = "V"
        val typeName = "void"

        val classType = DexClassType(typeString)
        Assert.assertEquals(typeName, DexClassTypeToString.toString(classType))
    }

    @Test
    fun convertsBoolProperly() {
        val typeString = "Z"
        val typeName = "bool"

        val classType = DexClassType(typeString)
        assertEquals(typeName, DexClassTypeToString.toString(classType))
    }

    @Test
    fun convertsByteProperly() {
        val typeString = "B"
        val typeName = "byte"

        val classType = DexClassType(typeString)
        assertEquals(typeName, DexClassTypeToString.toString(classType))
    }


    @Test
    fun convertsObjectProperly() {
        val typeString = "Ljava/lang/Throwable;"
        val typeName = "Ljava/lang/Throwable;"

        val classType = DexClassType(typeString)
        assertEquals(typeName, DexClassTypeToString.toString(classType))
    }

    @Test
    fun convertsIntProperly() {
        val typeString = "I"
        val typeName = "int"

        val classType = DexClassType(typeString)
        assertEquals(typeName, DexClassTypeToString.toString(classType))
    }

    @Test
    fun convertsLongProperly() {
        val typeString = "J"
        val typeName = "long"

        val classType = DexClassType(typeString)
        assertEquals(typeName, DexClassTypeToString.toString(classType))
    }

    @Test
    fun convertsShortProperly() {
        val typeString = "S"
        val typeName = "short"

        val classType = DexClassType(typeString)
        assertEquals(typeName, DexClassTypeToString.toString(classType))
    }

    @Test
    fun convertsFloatProperly() {
        val typeString = "F"
        val typeName = "float"

        val classType = DexClassType(typeString)
        assertEquals(typeName, DexClassTypeToString.toString(classType))
    }

    @Test
    fun convertsCharProperly() {
        val typeString = "C"
        val typeName = "char"

        val classType = DexClassType(typeString)
        assertEquals(typeName, DexClassTypeToString.toString(classType))
    }

    @Test
    fun convertsDoubleProperly() {
        val typeString = "D"
        val typeName = "double"

        val classType = DexClassType(typeString)
        assertEquals(typeName, DexClassTypeToString.toString(classType))
    }

    @Test
    fun convertsInitProperly() {
        val typeString = "<init>"
        val typeName = "<init>"

        val classType = DexClassType(typeString)
        assertEquals(typeName, DexClassTypeToString.toString(classType))
    }

    @Test
    fun convertsClinitProperly() {
        val typeString = "<clinit>"
        val typeName = "<clinit>"

        val classType = DexClassType(typeString)
        assertEquals(typeName, DexClassTypeToString.toString(classType))
    }

    @Test
    fun convertOneDimensionBoolArray() {
        val typeString = "[Z"
        val typeName = "bool[]"

        val classType = DexClassType(typeString)
        assertEquals(typeName, DexClassTypeToString.toString(classType))
    }

    @Test
    fun convertTwoDimensionBoolArray() {
        val typeString = "[[Z"
        val typeName = "bool[][]"

        val classType = DexClassType(typeString)
        assertEquals(typeName, DexClassTypeToString.toString(classType))
    }


    @Test
    fun convertOneDimensionObjectArray() {
        val typeString = "[Ljava/lang/Throwable;"
        val typeName = "Ljava/lang/Throwable;[]"

        val classType = DexClassType(typeString)
        assertEquals(typeName, DexClassTypeToString.toString(classType))
    }

    @Test
    fun convertTwoDimensionObjectArray() {
        val typeString = "[[Ljava/lang/Throwable;"
        val typeName = "Ljava/lang/Throwable;[][]"

        val classType = DexClassType(typeString)
        assertEquals(typeName, DexClassTypeToString.toString(classType))
    }


}