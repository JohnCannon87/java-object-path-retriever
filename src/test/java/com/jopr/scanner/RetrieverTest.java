package com.jopr.scanner;

import static org.junit.Assert.assertEquals;
import java.math.BigInteger;
import org.junit.Before;
import org.junit.Test;
import com.jopr.scanner.exception.PrimitiveTypesNotAllowedException;
import com.jopr.scanner.model.AdvancedTestClass;
import com.jopr.scanner.model.BasicTestClass;

public class RetrieverTest {

    @Before
    public void setup() {

    }

    @Test
    public void testRetrievalBasic() throws PrimitiveTypesNotAllowedException {
        // GIVEN
        BasicTestClass basicTestClass = new BasicTestClass("basicString", 42, 42.0, new BigInteger("42"));
        // WHEN
        String stringResult = Retriever.getValue(basicTestClass, "basicTestClass.basicString", String.class);
        int intResult = Retriever.getValue(basicTestClass, "basicTestClass.basicInt", Integer.class);
        Double doubleResult = Retriever.getValue(basicTestClass, "basicTestClass.objectDouble", Double.class);
        BigInteger bigIntegerResult =
                Retriever.getValue(basicTestClass, "basicClassTest.bigInteger", BigInteger.class);
        // THEN
        assertEquals("basicString", stringResult);
        assertEquals(42, intResult);
        assertEquals(new Double(42.0), doubleResult);
        assertEquals(new BigInteger("42"), bigIntegerResult);
    }

    @Test
    public void testRetrievalAdvanced() throws PrimitiveTypesNotAllowedException {
        // GIVEN
        BasicTestClass basicTestClass = new BasicTestClass("basicString", 42, 42.0, new BigInteger("42"));
        AdvancedTestClass advancedTestClass = new AdvancedTestClass(basicTestClass, 42L, 42.0f);
        // WHEN
        String stringResult =
                Retriever.getValue(advancedTestClass, "advancedTestClass.basicClass.basicString", String.class);
        int intResult = Retriever.getValue(advancedTestClass, "advancedTestClass.basicClass.basicInt", Integer.class);
        Double doubleResult =
                Retriever.getValue(advancedTestClass, "advancedTestClass.basicClass.objectDouble", Double.class);
        BigInteger bigIntegerResult =
                Retriever.getValue(advancedTestClass, "advancedTestClass.basicClass.bigInteger", BigInteger.class);
        Long longResult =
                Retriever.getValue(advancedTestClass, "advancedTestClass.basicLong", Long.class);
        Float floatResult =
                Retriever.getValue(advancedTestClass, "advancedTestClass.basicFloat", Float.class);
        // THEN
        assertEquals("basicString", stringResult);
        assertEquals(42, intResult);
        assertEquals(new Double(42.0), doubleResult);
        assertEquals(new BigInteger("42"), bigIntegerResult);
        assertEquals(new Long(42), longResult);
        assertEquals(new Float(42.0), floatResult);
    }

}
