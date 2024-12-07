package org.powerimo.common.exception;

public class ForbiddenException extends PowerimoHttpBasedException {

    public ForbiddenException() {
        super();
        this.httpCode = 403;
    }
}
