package org.powerimo.common.validation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ValidationResult {
    private final List<ValidationItem> items = new LinkedList<>();

    public ValidationResult addItem(ValidationItem item) {
        items.add(item);
        return this;
    }

    public ValidationResult addItem(String fieldName, String message, Severity severity) {
        return addItem(new ValidationItem(fieldName, message, severity));
    }

    public ValidationResult addError(String fieldName, String message) {
        return addItem(new ValidationItem(fieldName, message, Severity.ERROR));
    }

    public ValidationResult addWarning(String fieldName, String message) {
        return addItem(new ValidationItem(fieldName, message, Severity.WARN));
    }

    public ValidationResult addInfo(String fieldName, String message) {
        return addItem(new ValidationItem(fieldName, message, Severity.INFO));
    }

    public String buildMessage() {
        return items.stream()
                .map(item -> item.message)
                .collect(Collectors.joining("\r"));
    }

    @JsonIgnore
    public boolean hasErrors() {
        for (var item: items) {
            if (item.severity == Severity.ERROR)
                return true;
        }
        return false;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ValidationItem {
        private String fieldName;
        private String message;
        private Severity severity;
    }

    public enum Severity {
        INFO,
        WARN,
        ERROR
    }
}
