package org.powerimo.common.exception;

import org.junit.jupiter.api.Test;
import org.powerimo.common.exception.PowerimoCodedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PowerimoCodedExceptionTest {

    @Test
    void builderTest() {
        var inner = new RuntimeException();
        var ex = PowerimoCodedException.builder()
                .messageCode("ABC-123")
                .httpCode(400)
                .cause(inner)
                .details("SAMPLE")
                .build();

        assertEquals(400, ex.getHttpCode());
        assertEquals("ABC-123", ex.getMessageCode());
        assertEquals(inner, ex.getCause());
        assertEquals("SAMPLE", ex.getMessage());
    }

    @Test
    void builder2Test() {
        var ex = PowerimoCodedException.builder()
                .messageCode("ABC-123")
                .httpCode(400)
                .details("SAMPLE")
                .build();

        assertEquals(400, ex.getHttpCode());
        assertEquals("ABC-123", ex.getMessageCode());
        assertEquals("SAMPLE", ex.getMessage());
    }

    @Test
    void constructorTest() {
        var ex = new PowerimoCodedException("ABC-123");

        assertEquals(500, ex.getHttpCode());
        assertEquals("ABC-123", ex.getMessageCode());
        assertNull(ex.getMessage());
    }

    @Test
    void constructorInnerTest() {
        var inner = new RuntimeException();
        var ex = new PowerimoCodedException("ABC-123", inner);

        assertEquals(500, ex.getHttpCode());
        assertEquals("ABC-123", ex.getMessageCode());
        assertEquals(inner, ex.getCause());
        assertEquals(inner.toString(), ex.getMessage());
    }

}
