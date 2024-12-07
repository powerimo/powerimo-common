package org.powerimo.common.validation;

import org.junit.jupiter.api.Test;
import org.powerimo.common.validation.ValidationResult;

import static org.junit.jupiter.api.Assertions.*;

public class TestValidationResult {

    @Test
    void tesValidationItems() {
        ValidationResult validationResult = new ValidationResult()
                .addError("f1", "message1")
                .addError("f2", "message2")
                .addWarning("f3", "message3");

        assertEquals(3, validationResult.getItems().size());
        assertTrue(validationResult.hasErrors());
    }

    @Test
    void tesValidationItemsNonError() {
        ValidationResult validationResult = new ValidationResult()
                .addWarning("f3", "message3")
                .addInfo("f4", "message4");

        assertEquals(2, validationResult.getItems().size());
        assertFalse(validationResult.hasErrors());
    }

    @Test
    void tesDirectItem() {
        ValidationResult validationResult = new ValidationResult()
                .addItem("f5", "message5", ValidationResult.Severity.ERROR);

        assertEquals(1, validationResult.getItems().size());
        assertTrue(validationResult.hasErrors());
        var item = validationResult.getItems().get(0);
        assertEquals("f5", item.getFieldName());
        assertEquals("message5", item.getMessage());
        assertEquals(ValidationResult.Severity.ERROR, item.getSeverity());
    }

    @Test
    void testBuildMessage() {
        ValidationResult validationResult = new ValidationResult()
                .addItem("f6", "message6", ValidationResult.Severity.ERROR)
                .addError("f7", "message7");
        var msg = validationResult.buildMessage();
        assertNotNull(msg);
        assertEquals("message6\rmessage7", msg);
    }

}
