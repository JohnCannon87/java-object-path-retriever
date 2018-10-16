package com.jopr.scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.math.BigInteger;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import com.jopr.scanner.model.AdvancedTestClass;
import com.jopr.scanner.model.BasicTestClass;
import com.jopr.scanner.model.Node;

public class ScannerTest {

    @Before
    public void setup() {

    }

    @Test
    public void testGraphCreationBasic() {
        // GIVEN
        // WHEN
        List<Node> result = Scanner.getParameterGraphAsList(BasicTestClass.class);
        // THEN
        System.out.println(result);
        assertEquals(4, result.size());
        assertTrue(result.contains(new Node("basicTestClass.basicString", String.class)));
        assertTrue(result.contains(new Node("basicTestClass.basicInt", Integer.class)));
        assertTrue(result.contains(new Node("basicTestClass.objectDouble", Double.class)));
        assertTrue(result.contains(new Node("basicTestClass.bigInteger", BigInteger.class)));
    }

    @Test
    public void testGraphCreationAdvanced() {
        // GIVEN
        // WHEN
        List<Node> result = Scanner.getParameterGraphAsList(AdvancedTestClass.class);
        // THEN
        System.out.println(result);
        assertEquals(6, result.size());
        assertTrue(result.contains(new Node("advancedTestClass.basicLong", Long.class)));
        assertTrue(result.contains(new Node("advancedTestClass.basicFloat", Float.class)));
        assertTrue(result.contains(new Node("advancedTestClass.basicClass.basicString", String.class)));
        assertTrue(result.contains(new Node("advancedTestClass.basicClass.basicInt", Integer.class)));
        assertTrue(result.contains(new Node("advancedTestClass.basicClass.objectDouble", Double.class)));
        assertTrue(result.contains(new Node("advancedTestClass.basicClass.bigInteger", BigInteger.class)));
    }

}
