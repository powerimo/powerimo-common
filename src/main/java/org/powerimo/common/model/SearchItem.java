package org.powerimo.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchItem {
    private String fieldName;
    private SearchCondition condition;
    private Object value;
    private boolean ignoreIfNull = true;

    public static SearchItem of(String fieldName, SearchCondition condition, Object value) {
        return SearchItem.builder()
                .fieldName(fieldName)
                .condition(condition)
                .value(value)
                .build();
    }

    public static SearchItem of(String fieldName, Object value) {
        return SearchItem.builder()
                .fieldName(fieldName)
                .condition(SearchCondition.EQUALS)
                .value(value)
                .build();
    }
}
