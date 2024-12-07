package org.powerimo.common.resstrings;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CacheableResStringProvider implements ResStringProvider {
    private String defaultLocale;
    private final InMemorySource cacheSource;
    private final ResourceStringSource primarySource;

    public CacheableResStringProvider(String defaultLocale, ResourceStringSource primarySource) {
        this.defaultLocale = defaultLocale;
        this.cacheSource = new InMemorySource();
        this.primarySource = primarySource;
    }

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

    @Override
    public String getResourceText(String code, String locale) {
        var record = getResourceRecord(code);
        if (record == null) {
            return "There is no record in ResString storage for code '" + code + "'.";
        }
        return record.getText(locale);
    }

    @Override
    public String getResourceText(String code) {
        if (defaultLocale == null)
            throw new ResStringException("Default locale is not set for ReStringProvider.");
        return getResourceText(code, defaultLocale);
    }

    @Override
    public String getDefaultLocale() {
        return defaultLocale;
    }

    @Override
    public void setDefaultLocale(String locale) {
        this.defaultLocale = locale;
    }
}
