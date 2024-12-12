package org.powerimo.common.resstrings;

import lombok.Getter;
import lombok.Setter;

/**
 * CacheableResStringProvider is an implementation of the ResStringProvider interface that uses
 * a two-tiered resource lookup system. It first checks an in-memory cache and, if the resource
 * is not found, retrieves it from a primary source and caches it for future usage. This approach
 * improves performance by minimizing repeated access to the primary data source.
 *
 * This class supports the following functionality:
 * - Fetching localized resource strings for specific codes and locales.
 * - Caching resource records in memory for quicker future access.
 * - Setting and retrieving a default locale for localization.
 * - Handling missing resources or cases where the primary resource source is undefined.
 *
 * Constructor:
 * - The constructor initializes the CacheableResStringProvider object by setting a default locale,
 * creating an instance of the in-memory cache, and specifying the primary resource source.
 *
 * Methods:
 * - getResourceRecord: Retrieves a resource record from the cache or primary source. If the record
 *   is found in the primary source but not in the cache, it will be added to the cache.
 * - getResourceText(String code, String locale): Fetches the localized text for a given resource
 *   code and locale. Throws exceptions for missing resources or uninitialized sources.
 * - getResourceText(String code): Fetches the localized text for a given resource code using the
 *   default locale. An exception is thrown if the default locale is not set.
 * - getDefaultLocale: Returns the default locale currently set.
 * - setDefaultLocale: Updates the default locale.
 *
 * Exceptions:
 * - Throws ResStringException if the primary source is not set when attempting to fetch a resource
 *   or if the default locale is not set.
 */
@Getter
@Setter
public class CacheableResStringProvider implements ResStringProvider {
    private String defaultLocale;
    private final InMemorySource cacheSource;
    private final ResourceStringSource primarySource;

    /**
     * Constructs a CacheableResStringProvider instance with a default locale and a primary resource string source.
     *
     * @param defaultLocale The default locale used for resource string retrieval when no specific locale is provided.
     * @param primarySource The primary ResourceStringSource implementation used to retrieve resource records.
     */
    public CacheableResStringProvider(String defaultLocale, ResourceStringSource primarySource) {
        this.defaultLocale = defaultLocale;
        this.cacheSource = new InMemorySource();
        this.primarySource = primarySource;
    }

    /**
     * Retrieves a ResourceRecord associated with the specified code. If the record exists in
     * the cache source, it is returned directly. Otherwise, it attempts to retrieve the record
     * from the primary source. If successful, the record is added to the cache for future use.
     * If the primary source is null or the record is not found, appropriate actions are taken.
     *
     * @param code the unique identifier of the resource record to retrieve.
     * @return the ResourceRecord associated with the given code, or null if not found and the
     *         primary source is empty or the record does not exist.
     * @throws ResStringException if the primary source is null.
     */
    @Override
    public ResourceRecord getResourceRecord(String code) {
        if (cacheSource.isExists(code)) {
            return cacheSource.getResourceRecord(code);
        }
        if (primarySource == null)
            throw new ResStringException("PrimarySource is empty for ResString provider");
        var record = primarySource.getResourceRecord(code);
        if (record != null) {
            cacheSource.addResourceRecord(record);
            return record;
        }
        return null;
    }

    /**
     * Retrieves the localized text associated with a specific resource code and locale.
     * If the resource code is not found, a default error message is returned.
     *
     * @param code the unique identifier of the resource string to retrieve
     * @param locale the locale for which the resource text is requested
     * @return the localized text corresponding to the provided code and locale, or an error message if the code is not found
     */
    @Override
    public String getResourceText(String code, String locale) {
        var record = getResourceRecord(code);
        if (record == null) {
            return "There is no record in ResString storage for code '" + code + "'.";
        }
        return record.getText(locale);
    }

    /**
     * Retrieves the resource text associated with the specified code using the default locale.
     *
     * @param code the unique identifier of the resource string to retrieve
     * @return the resource text corresponding to the specified code in the default locale
     * @throws ResStringException if the default locale is not set
     */
    @Override
    public String getResourceText(String code) {
        if (defaultLocale == null)
            throw new ResStringException("Default locale is not set for ReStringProvider.");
        return getResourceText(code, defaultLocale);
    }

    /**
     * Retrieves the default locale configured for the provider.
     *
     * @return the default locale as a string.
     */
    @Override
    public String getDefaultLocale() {
        return defaultLocale;
    }

    /**
     * Sets the default locale for this resource string provider.
     * The specified locale will be used as the default in cases where no specific locale is provided.
     *
     * @param locale the default locale to be set, represented as a string (e.g., "en", "fr").
     */
    @Override
    public void setDefaultLocale(String locale) {
        this.defaultLocale = locale;
    }
}
