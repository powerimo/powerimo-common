package org.powerimo.common.exception;

public class PowerimoException extends Exception {
    public PowerimoException() {
        super();
    }

    public PowerimoException(String message) {
        super(message);
    }

    public PowerimoException(String message, Throwable cause) {
        super(message, cause);
    }

    public PowerimoException(Throwable cause) {
        super(cause);
    }
}
