package org.powerimo.common.exception;

import lombok.Getter;

@Getter
public class PowerimoCodedException extends PowerimoHttpBasedException {
    private String messageCode;

    public PowerimoCodedException(String code) {
        this.messageCode = code;
    }

    public PowerimoCodedException(String code, Throwable cause) {
        super(cause);
        this.messageCode = code;
    }

    public PowerimoCodedException(String code, String details) {
        super(details);
        this.messageCode = code;
    }

    public PowerimoCodedException(String code, String details, Throwable cause) {
        super(details, cause);
        this.messageCode = code;
    }

    public PowerimoCodedException(String code, String details, int httpCode) {
        super(details);
        this.messageCode = code;
        this.httpCode = httpCode;
    }

    public PowerimoCodedException(String code, String details, int httpCode, Throwable cause) {
        super(details, cause);
        this.messageCode = code;
        this.httpCode = httpCode;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int httpCode = 500;
        private String messageCode;
        private String details;
        private Throwable cause;

        public Builder details(String details) {
            this.details = details;
            return this;
        }

        public Builder httpCode(int code) {
            this.httpCode = code;
            return this;
        }

        public Builder messageCode(String code) {
            this.messageCode = code;
            return this;
        }

        public Builder cause(Throwable e) {
            this.cause = e;
            return this;
        }

        public PowerimoCodedException build() {
            if (cause != null) {
                return new PowerimoCodedException(messageCode, details, httpCode, cause);
            }
            return new PowerimoCodedException(messageCode, details, httpCode);
        }
    }

}
