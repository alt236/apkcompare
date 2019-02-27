package uk.co.alt236.apkcompare.repo.dex.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class DexClassTypeTest {

    @Test
    public void parsesNormalClassesCorrectly() {
        final String classType = "Luk/co/abc/def/ghi/jkl/className;";
        final DexClassType dexClassType = new DexClassType(classType);

        assertEquals(classType, dexClassType.getType());
        assertEquals("className", dexClassType.getClassSimpleName());
        assertEquals("uk.co.abc.def.ghi.jkl", dexClassType.getPackageName());
        assertFalse(dexClassType.isInnerClass());
        assertFalse(dexClassType.isLambda());
    }

    @Test
    public void parsesInnerClassesCorrectly() {
        final String classType = "Luk/co/abc/def/ghi/jkl/className$0;";
        final DexClassType dexClassType = new DexClassType(classType);

        assertEquals(classType, dexClassType.getType());
        assertEquals("className$0", dexClassType.getClassSimpleName());
        assertEquals("uk.co.abc.def.ghi.jkl", dexClassType.getPackageName());
        assertTrue(dexClassType.isInnerClass());
        assertFalse(dexClassType.isLambda());
    }

    @Test
    public void parsesLambdasCorrectly() {
        final String classType = "Luk/co/abc/def/ghi/jkl/className$$Lambda$0;";
        final DexClassType dexClassType = new DexClassType(classType);

        assertEquals(classType, dexClassType.getType());
        assertEquals("className$$Lambda$0", dexClassType.getClassSimpleName());
        assertEquals("uk.co.abc.def.ghi.jkl", dexClassType.getPackageName());
        assertTrue(dexClassType.isInnerClass());
        assertTrue(dexClassType.isLambda());
    }

    @Test
    public void parsesVoidCorrectly() {
        final String classType = "V";
        final DexClassType dexClassType = new DexClassType(classType);

        assertEquals(classType, dexClassType.getType());
        assertEquals("V", dexClassType.getClassSimpleName());
        assertEquals("", dexClassType.getPackageName());
        assertFalse(dexClassType.isInnerClass());
        assertFalse(dexClassType.isLambda());
    }

    @Test
    public void twoClassTypesWithTheSameParameterAreEqual() {
        final String classType1 = "Luk/co/abc/def/ghi/jkl/className$$Lambda$0;";
        final String classType2 = "Luk/co/abc/def/ghi/jkl/className$$Lambda$0;";

        final DexClassType dexClassType1 = new DexClassType(classType1);
        final DexClassType dexClassType2 = new DexClassType(classType2);

        assertEquals(dexClassType1, dexClassType2);
        assertEquals(dexClassType2, dexClassType1);
    }

    @Test
    public void twoClassTypesWithTheSameParameterHaveSameHashCode() {
        final String classType1 = "Luk/co/abc/def/ghi/jkl/className$$Lambda$0;";
        final String classType2 = "Luk/co/abc/def/ghi/jkl/className$$Lambda$0;";

        final DexClassType dexClassType1 = new DexClassType(classType1);
        final DexClassType dexClassType2 = new DexClassType(classType2);

        assertEquals(dexClassType1.hashCode(), dexClassType2.hashCode());
    }
}