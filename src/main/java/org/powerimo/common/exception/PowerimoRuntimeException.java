package org.powerimo.common.exception;

public class PowerimoRuntimeException extends RuntimeException {
    public PowerimoRuntimeException() {
        super();
    }

    public PowerimoRuntimeException(String message) {
        super(message);
    }

    public PowerimoRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public PowerimoRuntimeException(Throwable cause) {
        super(cause);
    }
}
