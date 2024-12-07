package org.powerimo.common.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.powerimo.common.model.SearchCondition;
import org.powerimo.common.model.SearchItem;
import org.powerimo.common.model.SearchQuery;

import static org.junit.jupiter.api.Assertions.*;

class SearchQueryTest {
    private SearchQuery searchQuery;

    @BeforeEach
    void init() {
        searchQuery = new SearchQuery();
    }

    @Test
    void checkItems() {
        assertNull(searchQuery.getItems());
        searchQuery.checkItems();
        assertNotNull(searchQuery.getItems());
    }

    @Test
    void add_1() {
        searchQuery.add(SearchItem.of("a1", SearchCondition.CONTAINS, "v1"));
        assertEquals(1, searchQuery.getItems().size());
    }

    @Test
    void add_2() {
        searchQuery.add(SearchItem.of("a1", "v1"));
        assertEquals(1, searchQuery.getItems().size());
        var item = searchQuery.getItems().get(0);
        assertEquals("a1", item.getFieldName());
        assertEquals("v1", item.getValue());
        assertEquals(SearchCondition.EQUALS, item.getCondition());
    }

    @Test
    void add_3() {
        searchQuery.addEqual("a1", "v1");
        assertEquals(1, searchQuery.getItems().size());
        var item = searchQuery.getItems().get(0);
        assertEquals("a1", item.getFieldName());
        assertEquals("v1", item.getValue());
        assertEquals(SearchCondition.EQUALS, item.getCondition());
    }

    @Test
    void add_4() {
        searchQuery.add("a1", SearchCondition.CONTAINS, "v1");
        assertEquals(1, searchQuery.getItems().size());
        var item = searchQuery.getItems().get(0);
        assertEquals("a1", item.getFieldName());
        assertEquals("v1", item.getValue());
        assertEquals(SearchCondition.CONTAINS, item.getCondition());
    }

    @Test
    void add_5() {
        searchQuery.add("a1", SearchCondition.GREATER, 123);
        assertEquals(1, searchQuery.getItems().size());
        var item = searchQuery.getItems().get(0);
        assertEquals("a1", item.getFieldName());
        assertEquals(123, item.getValue());
        assertEquals(SearchCondition.GREATER, item.getCondition());
    }

    @Test
    void limit() {
        searchQuery.setLimit(10L);
        searchQuery.setOffset(20L);
        searchQuery.setOrderBy("SortField");
        assertEquals(10L, searchQuery.getLimit());
        assertEquals(20L, searchQuery.getOffset());
        assertEquals("SortField", searchQuery.getOrderBy());
    }

    @Test
    void builder() {
        SearchQuery q = SearchQuery.builder()
                .limit(10L)
                .build();
        assertEquals(10L, q.getLimit());
    }

    @Test
    void constructor() {
        SearchQuery q = new SearchQuery()
                .addEqual("a1", "v1")
                .addEqual("a2", 123);
        assertEquals(2, q.getItems().size());
    }

}