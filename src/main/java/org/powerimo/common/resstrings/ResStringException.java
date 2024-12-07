package org.powerimo.common.resstrings;

public class ResStringException extends RuntimeException {
    public ResStringException(String message) {
        super(message);
    }

    public ResStringException(String message, Throwable cause) {
        super(message, cause);
    }
}
