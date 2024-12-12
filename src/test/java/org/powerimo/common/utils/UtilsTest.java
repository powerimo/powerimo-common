package org.powerimo.common.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.powerimo.common.utils.Utils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {

    @Test
    void testFormatLogValue() {
        var name = "param1";
        var value = "value1";
        var res = Utils.formatLogValue(name, value, 30);
        assertNotNull(res);
        assertEquals(30, res.length());
        assertEquals("param1..................value1", res);
    }

    @Test
    void testFormatLogValueShorter() {
        var name = "param1";
        var value = "value1";
        var res = Utils.formatLogValue(name, value, 10);
        assertNotNull(res);
        assertEquals("param1...value1", res);
    }

    @Test
    void formatLogValueDefaultTest() {
        var name = "param1";
        var value = "value1";
        var res = Utils.formatLogValue(name, value);
        assertNotNull(res);
        assertEquals(Utils.DEFAULT_LOG_LINE_LENGTH, res.length());
    }

    @Test
    void formatLogValueCompletedTest() {
        var name = "sample";
        var value = Utils.formatLogValueCompleted(name);
        assertNotNull(value);
        assertEquals("sample.................................................................COMPLETED", value);
    }

    @Test
    void formatLogValueStartedTest() {
        var name = "sample";
        var value = Utils.formatLogValueStarted(name);
        assertNotNull(value);
        assertEquals("sample...................................................................STARTED", value);
    }

    @Test
    public void maskStringTest() {
        String s = "1234567890";
        var m = Utils.masqueradeKey(s, 3, 1);
        Assertions.assertEquals("123**************0", m);
    }

    @Test
    public void maskStringTestIncorrectTest() {
        String s = "12";
        var m = Utils.masqueradeKey(s, 3, 5);
        Assertions.assertEquals("**************12", m);
    }

    @Test
    @SuppressWarnings("all")
    void masqueradeKeyEmptyTest() {
        var m = Utils.masqueradeKey(null, 10, 1);
        assertNull(m);
    }


    @Test
    void readTextResourceTest() throws IOException {
        var txt = Utils.readTextResource("test_resource.txt");
        Assertions.assertEquals("Sample text", txt);
    }

    @Test
    void readTextResourceExceptionTest() {
        Assertions.assertThrows(IOException.class, () -> Utils.readTextResource("non_existing_test_resource.txt") );
    }

    @Test
    void strToIntDefCorrectTest() {
        String s = "10";
        assertEquals(10, Utils.stringToIntegerDef(s, 5));
    }

    @Test
    void strToIntDefIncorrectTest() {
        String s = "a10";
        assertEquals(5, Utils.stringToIntegerDef(s, 5));
    }

    @Test
    void extractSourceExceptionTextSingleExceptionTest() {
        var exception = new Exception("Root exception");
        var result = Utils.extractSourceExceptionText(exception);
        assertNotNull(result);
        assertEquals("Root exception", result);
    }

    @Test
    void extractSourceExceptionTextNestedExceptionTest() {
        var innerException = new Exception("Inner exception");
        var outerException = new Exception("Outer exception", innerException);
        var result = Utils.extractSourceExceptionText(outerException);
        assertNotNull(result);
        assertEquals("Inner exception", result);
    }

    @Test
    void extractSourceExceptionTextNullTest() {
        var result = Utils.extractSourceExceptionText(null);
        assertNull(result);
    }
}
