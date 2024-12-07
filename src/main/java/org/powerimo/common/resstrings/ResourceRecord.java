package org.powerimo.common.resstrings;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class ResourceRecord {
    private String code;
    private HashMap<String, String> localizedText = new HashMap<>();
    private Integer httpCode;

    public void addText(String locale, String text) {
        localizedText.put(locale, text);
    }

    public String getText(String locale) {
        return localizedText.get(locale);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String code;
        private Integer httpCode;
        public HashMap<String, String> localizedText = new HashMap<>();

        public ResourceRecord build() {
            ResourceRecord record = new ResourceRecord();
            record.code = this.code;
            record.httpCode = this.httpCode;
            record.localizedText.putAll(localizedText);
            return record;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder httpCode(int code) {
            this.httpCode = code;
            return this;
        }

        public Builder text(String locale, String message) {
            this.localizedText.put(locale, message);
            return this;
        }
    }
}
