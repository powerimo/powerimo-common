package org.powerimo.common.exception;

import org.junit.jupiter.api.Test;
import org.powerimo.common.exception.PowerimoHttpBasedException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PowerimoHttpBaseExceptionTest {

    @Test
    void baseConstructorTest() {
        var e = new PowerimoHttpBasedException();

        assertEquals(500, e.getHttpCode());
    }

    @Test
    void baseConstructorInnerTest() {
        var inner = new RuntimeException();
        var e = new PowerimoHttpBasedException(inner);

        assertEquals(500, e.getHttpCode());
        assertEquals(inner, e.getCause());
    }

    @Test
    void baseConstructorMessageInnerTest() {
        var inner = new RuntimeException();
        var e = new PowerimoHttpBasedException("msg", inner);

        assertEquals(500, e.getHttpCode());
        assertEquals(inner, e.getCause());
        assertEquals("msg", e.getMessage());
    }

    @Test
    void constructorMessageTest() {
        var e = new PowerimoHttpBasedException("Message text");

        assertEquals(500, e.getHttpCode());
        assertEquals("Message text", e.getMessage());
    }

    @Test
    void constructorHttpTest() {
        var e = new PowerimoHttpBasedException(404);

        assertEquals(404, e.getHttpCode());
    }

    @Test
    void constructorMessageHttpTest() {
        var e = new PowerimoHttpBasedException("msg", 404);

        assertEquals("msg", e.getMessage());
        assertEquals(404, e.getHttpCode());
    }

    @Test
    void constructorMessageInnerHttpTest() {
        var inner = new RuntimeException();
        var e = new PowerimoHttpBasedException("msg", inner, 403);

        assertEquals("msg", e.getMessage());
        assertEquals(403, e.getHttpCode());
        assertEquals(inner, e.getCause());
    }

    @Test
    void constructorInnerHttpTest() {
        var inner = new RuntimeException();
        var e = new PowerimoHttpBasedException(inner, 400);

        assertEquals(400, e.getHttpCode());
        assertEquals(inner, e.getCause());
    }

    @Test
    void setterTest() {
        var e = new PowerimoHttpBasedException();
        e.setHttpCode(401);

        assertEquals(401, e.getHttpCode());
    }
}
