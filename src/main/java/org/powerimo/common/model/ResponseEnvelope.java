package org.powerimo.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;

@Data
@Builder
@AllArgsConstructor
public class ResponseEnvelope<T> implements Serializable {
    private String message;
    private T data;
    protected int httpStatus = HttpServletResponse.SC_OK;
    protected String path;
    protected LocalDateTime timestamp = LocalDateTime.now(ZoneOffset.UTC);
    protected String dataClassName;
    protected HashMap<String, Object> details = new HashMap<>();
    private static final int DEFAULT_ERROR_STATUS = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

    public ResponseEnvelope() {
        message = null;
    }

    public ResponseEnvelope(String message) {
        this.message = message;
    }

    public ResponseEnvelope(String message, String path) {
        this.message = message;
        this.path = path;
    }

    public ResponseEnvelope(String message, String path, int status1) {
        this.message = message;
        this.path = path;
        httpStatus = status1;
    }

    public ResponseEnvelope<?> setStatus(int status1) {
        httpStatus = status1;
        return this;
    }

    public HashMap<String, Object> getDetails() {
        return details;
    }

    public void addDetail(String s, Object v) {
        details.put(s, v);
    }

    public void setRequest(HttpServletRequest request) {
        if (request != null)
            this.path = request.getRequestURI();
        else
            this.path = null;
    }

    public void setData(T data1) {
        data = data1;
        if (data1 == null) {
            dataClassName = null;
        }
        else {
            dataClassName = data1.getClass().getCanonicalName();
        }
    }

    public static <T> ResponseEnvelope<T> of(T payload) {
        var res = new ResponseEnvelope<T>();
        res.setData(payload);
        return res;
    }

    public static <T> ResponseEnvelope<T> of(T payload, HttpServletRequest request) {
        var res = new ResponseEnvelope<T>();
        res.setData(payload);
        res.setRequest(request);
        return res;
    }

    public static ResponseEnvelope<?> error(String message) {
        return ResponseEnvelope.builder()
                .message(message)
                .httpStatus(DEFAULT_ERROR_STATUS)
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
    }

    public static ResponseEnvelope<?> error(String message, int httpStatus) {
        return ResponseEnvelope.builder()
                .message(message)
                .httpStatus(httpStatus)
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
    }

    public static ResponseEnvelope<?> error(String message, int httpStatus, HttpServletRequest request) {
        var envelope = ResponseEnvelope.builder()
                .message(message)
                .httpStatus(httpStatus)
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        envelope.setRequest(request);
        return envelope;
    }

    public static ResponseEnvelope<?> error(Throwable ex) {
        return ResponseEnvelope.builder()
                .message(ex.getMessage())
                .httpStatus(DEFAULT_ERROR_STATUS)
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
    }

    public static ResponseEnvelope<?> error(Throwable ex, int httpStatus) {
        return ResponseEnvelope.builder()
                .message(ex.getMessage())
                .httpStatus(httpStatus)
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
    }

    public static ResponseEnvelope<?> error(Throwable ex, int httpStatus, HttpServletRequest request) {
        var envelope = ResponseEnvelope.builder()
                .message(ex.getMessage())
                .httpStatus(httpStatus)
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        envelope.setRequest(request);
        return envelope;
    }

}

