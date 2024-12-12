package org.powerimo.common.resstrings;

import lombok.Getter;

/**
 * ResourceNotFound is a custom runtime exception that is thrown when a resource
 * with the specified code cannot be found. This exception is primarily used in
 * resource string management to indicate that a particular resource is missing.
 * <p>
 * The exception includes the resource code that caused the error, providing
 * detailed context for debugging and error handling.
 * <p>
 * This class extends {@code RuntimeException}, enabling unchecked exception
 * processing and simplifying exception handling in resource management workflows.
 */
@Getter
public class ResourceNotFound extends RuntimeException {
    private final String code;

    /**
     * Constructs a new ResourceNotFound exception with a detailed message for the
     * specified resource code. The message provides context indicating that a
     * resource with the given code could not be found.
     *
     * @param code the unique identifier of the resource that could not be found
     */
    public ResourceNotFound(String code) {
        super("Resource with code '" + code + "' was not found");
        this.code = code;
    }

}
