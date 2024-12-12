package org.powerimo.common.resstrings;

import java.util.HashMap;

/**
 * InMemorySource is an implementation of the ResourceStringSource interface that stores
 * resource records in an in-memory HashMap. This class provides a mechanism to manage
 * resource strings by adding, retrieving, and checking the existence of resource records
 * using their unique codes.
 * <p>
 * The storage is transient and only exists in memory during the runtime of the application.
 * It is intended for scenarios where a lightweight and fast resource string management
 * system is needed without external dependencies.
 */
public class InMemorySource implements ResourceStringSource {
    private final HashMap<String, ResourceRecord> items = new HashMap<>();

    /**
     * Retrieves a {@link ResourceRecord} from the in-memory storage using the specified code.
     * If the code cannot be found, a {@link ResourceNotFound} exception is thrown.
     *
     * @param code the unique identifier associated with the resource record to retrieve
     * @return the {@link ResourceRecord} associated with the given code
     * @throws ResourceNotFound if no resource record with the specified code is found
     */
    @Override
    public ResourceRecord getResourceRecord(String code) {
        try {
            return items.get(code);
        } catch (Exception ex) {
            throw new ResourceNotFound(code);
        }
    }

    /**
     * Adds a new resource record to the in-memory storage.
     * If a resource record with the same code already exists, it will be replaced.
     *
     * @param record The {@code ResourceRecord} to be added. This object must contain
     *               a unique code which is used as the key in the storage.
     */
    @Override
    public void addResourceRecord(ResourceRecord record) {
        items.put(record.getCode(), record);
    }

    /**
     * Checks if a resource record with the specified code exists in the current storage.
     *
     * @param code the unique code identifying the resource record to check
     * @return true if a resource record with the specified code exists, false otherwise
     */
    @Override
    public boolean isExists(String code) {
        return items.containsKey(code);
    }
}
