package org.powerimo.common.resstrings;

import java.util.HashMap;

public class InMemorySource implements ResourceStringSource {
    private final HashMap<String, ResourceRecord> items = new HashMap<>();

    @Override
    public ResourceRecord getResourceRecord(String code) {
        try {
            return items.get(code);
        } catch (Exception ex) {
            throw new ResourceNotFound(code);
        }
    }

    @Override
    public void addResourceRecord(ResourceRecord record) {
        items.put(record.getCode(), record);
    }

    @Override
    public boolean isExists(String code) {
        return items.containsKey(code);
    }
}
