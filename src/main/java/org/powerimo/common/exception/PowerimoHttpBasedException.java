package org.powerimo.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PowerimoHttpBasedException extends PowerimoRuntimeException {
    protected int httpCode = 500;

    public PowerimoHttpBasedException() {
        this.httpCode = 500;
    }

    public PowerimoHttpBasedException(int httpCode) {
        this.httpCode = httpCode;
    }

    public PowerimoHttpBasedException(String message) {
        super(message);
    }

    public PowerimoHttpBasedException(String message, int httpCode) {
        super(message);
        this.httpCode = httpCode;
    }

    public PowerimoHttpBasedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PowerimoHttpBasedException(String message, Throwable cause, int httpCode) {
        super(message, cause);
        this.httpCode = httpCode;
    }

    public PowerimoHttpBasedException(Throwable cause) {
        super(cause);
    }

    public PowerimoHttpBasedException(Throwable cause, int httpCode) {
        super(cause);
        this.httpCode = httpCode;
    }
}
