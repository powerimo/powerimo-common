package org.powerimo.common.exception;

import org.junit.jupiter.api.Test;
import org.powerimo.common.exception.ValidationException;
import org.powerimo.common.validation.ValidationResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestValidationException {

    @Test
    void testConstructor1() {
        ValidationResult v = new ValidationResult();
        var ex = new ValidationException(v);

        assertEquals(v, ex.getValidationResult());
    }

    @Test
    void testConstructor2() {
        ValidationResult v = new ValidationResult();
        var ex = new ValidationException(v, "message");

        assertEquals(v, ex.getValidationResult());
        assertEquals("message", ex.getMessage());
    }

}
