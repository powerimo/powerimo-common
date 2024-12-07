package org.powerimo.common.resstrings;

import lombok.Getter;

@Getter
public class ResourceNotFound extends RuntimeException {
    private final String code;

    public ResourceNotFound(String code) {
        super("Resource with code '" + code + "' was not found");
        this.code = code;
    }

}
