package org.powerimo.common.resstrings;

/**
 * The ResStringProvider interface defines the contract for resolving and managing resource strings
 * associated with specific codes. It facilitates localization by providing methods to fetch
 * resource texts based on codes and locales, manage default locale settings, and retrieve full
 * resource records.
 *
 * Methods in this interface allow implementations to interact with underlying storage mechanisms
 * for resource strings, ensuring flexibility and extensibility for custom implementations.
 */
public interface ResStringProvider {
    ResourceRecord getResourceRecord(String code);
    String getResourceText(String code, String locale);
    String getResourceText(String code);
    String getDefaultLocale();
    void setDefaultLocale(String locale);
}
