package org.powerimo.common.exception;

import lombok.Getter;
import org.powerimo.common.validation.ValidationResult;

@Getter
public class ValidationException extends RuntimeException {
    private final ValidationResult validationResult;

    public ValidationException(ValidationResult result) {
        this.validationResult = result;
    }

    public ValidationException(ValidationResult result, String message) {
        super(message);
        this.validationResult = result;
    }

}
