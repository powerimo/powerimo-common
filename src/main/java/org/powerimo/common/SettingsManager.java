package org.powerimo.common;

public interface SettingsManager {
    String getSetting(final String name) throws Exception;
    void setSetting(final String name, final String value) throws Exception;
}
