package org.powerimo.common.utils;

import org.powerimo.common.model.ResponseEnvelope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class AlternativeResponseBuilder {
    private int status = HttpServletResponse.SC_OK;
    private Object payload;
    private String msg;
    private String path;

    public static AlternativeResponseBuilder builder() {
        return new AlternativeResponseBuilder();
    }

    public ResponseEnvelope buildEnvelope() {
        var r = ResponseEnvelope.builder()
                .httpStatus(status)
                .message(msg)
                .path(path)
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        r.setData(payload);
        return r;
    }

    public ResponseEnvelope build() {
        return buildEnvelope();
    }

    public AlternativeResponseBuilder status(int status1) {
        this.status = status1;
        return this;
    }

    public AlternativeResponseBuilder message(String message1) {
        this.msg = message1;
        return this;
    }

    public AlternativeResponseBuilder path(String path) {
        this.path = path;
        return this;
    }

    public AlternativeResponseBuilder payload(Object payload) {
        this.payload = payload;
        return this;
    }

    public AlternativeResponseBuilder path(HttpServletRequest request) {
        this.path = request.getRequestURI();
        return this;
    }

}
