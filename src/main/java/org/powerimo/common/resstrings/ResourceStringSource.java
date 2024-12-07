package org.powerimo.common.resstrings;

public interface ResourceStringSource {
    ResourceRecord getResourceRecord(String code);
    void addResourceRecord(ResourceRecord record);
    boolean isExists(String code);
}
