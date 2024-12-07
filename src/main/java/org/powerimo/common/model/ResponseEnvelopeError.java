package org.powerimo.common.model;

import lombok.*;

import javax.servlet.http.HttpServletResponse;

@Getter
@Setter
@ToString
public class ResponseEnvelopeError<T> extends ResponseEnvelope<T> {
    private String additionalErrorMessage;

    public ResponseEnvelopeError() {
        super();
        httpStatus = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }

    public ResponseEnvelopeError(String message) {
        super(message);
        httpStatus = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }

    public ResponseEnvelopeError(String message, String path) {
        super(message, path);
        httpStatus = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }

    public ResponseEnvelopeError(String message, String path, int status1) {
        super(message, path, status1);
    }

    public ResponseEnvelopeError(String message, String path, String additionalErrorMessage) {
        super(message, path);
        httpStatus = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        this.additionalErrorMessage = additionalErrorMessage;
    }

    public ResponseEnvelopeError(String message, String path, String additionalErrorMessage, int status1) {
        super(message, path, status1);
        this.additionalErrorMessage = additionalErrorMessage;
    }

}