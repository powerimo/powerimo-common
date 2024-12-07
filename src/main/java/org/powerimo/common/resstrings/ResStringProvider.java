package org.powerimo.common.resstrings;

public interface ResStringProvider {
    ResourceRecord getResourceRecord(String code);
    String getResourceText(String code, String locale);
    String getResourceText(String code);
    String getDefaultLocale();
    void setDefaultLocale(String locale);
}
