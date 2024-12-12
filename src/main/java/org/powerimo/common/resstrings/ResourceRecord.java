package org.powerimo.common.resstrings;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

/**
 * ResourceRecord is a data model that represents a localized resource with an associated HTTP code.
 * It contains a unique identifier (code), a set of localized texts corresponding to different locales,
 * and an HTTP status code. This class provides utility methods to manage and retrieve resource text
 * based on a locale.
 * <p>
 * The class also includes a builder for convenient construction of instances with specified properties.
 */
@Getter
@Setter
public class ResourceRecord {
    private String code;
    private HashMap<String, String> localizedText = new HashMap<>();
    private Integer httpCode;

    /**
     * Adds a localized text to the resource record for a specified locale.
     *
     * @param locale the locale identifier (e.g., "en", "fr", "es") to associate with the text
     * @param text   the localized text to be stored for the specified locale
     */
    public void addText(String locale, String text) {
        localizedText.put(locale, text);
    }

    /**
     * Retrieves the localized text associated with the specified locale.
     *
     * @param locale the locale identifier for which the resource text is to be retrieved
     * @return the localized text corresponding to the given locale, or null if no text is found
     */
    public String getText(String locale) {
        return localizedText.get(locale);
    }

    /**
     * Creates and returns a new instance of the {@code Builder}, which can be used
     * to construct a {@code ResourceRecord} with custom properties.
     *
     * @return a new {@code Builder} instance for creating a {@code ResourceRecord}
     */
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
