package org.powerimo.common.resstrings;

/**
 * The ResourceStringSource interface provides a contract for managing resource strings
 * associated with specific codes. Implementations of this interface enable retrieval,
 * addition, and existence checking of resource records.
 * This interface is designed to support various storage mechanisms such as in-memory,
 * database, or other custom implementations.
 */
public interface ResourceStringSource {
    ResourceRecord getResourceRecord(String code);
    void addResourceRecord(ResourceRecord record);
    boolean isExists(String code);
}
