package org.powerimo.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchQuery {
    private List<SearchItem> items;
    private String orderBy;
    private Long limit;
    private Long offset;

    protected void checkItems() {
        if (items == null)
            items = new ArrayList<>();
    }

    public SearchQuery add(SearchItem item) {
        checkItems();
        items.add(item);
        return this;
    }

    public SearchQuery add(String fieldName, SearchCondition condition, Object value) {
        add(SearchItem.builder()
                .fieldName(fieldName)
                .condition(condition)
                .value(value)
                .build());
        return this;
    }

    public SearchQuery addEqual(String fieldName, Object value) {
        add(SearchItem.builder()
                .fieldName(fieldName)
                .condition(SearchCondition.EQUALS)
                .value(value)
                .build());
        return this;
    }

}
