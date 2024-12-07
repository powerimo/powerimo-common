package org.powerimo.common.utils;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.powerimo.common.utils.DatabaseUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DatabaseUtilsTest {

    @Mock
    private ResultSet resultSet = mock(ResultSet.class);

    @Test
    void testIsFieldExists() throws SQLException {
        when(resultSet.findColumn("id")).thenReturn(1);
        boolean result = DatabaseUtils.isFieldExists(resultSet, "id");
        assertTrue(result);
    }

    @Test
    void testIsFieldNotExists() throws SQLException {
        when(resultSet.findColumn(any())).thenReturn(-1);
        boolean result = DatabaseUtils.isFieldExists(resultSet, "non-existing-field");
        assertFalse(result);
    }

    @Test
    void testEnumNonNull() throws SQLException {
        when(resultSet.getString("status1")).thenReturn("STATUS1");
        when(resultSet.getString("status2")).thenReturn("STATUS2");

        var v1 = DatabaseUtils.readEnumValue(resultSet, "status1", SampleEnum.class);
        assertNotNull(v1);
        assertEquals(SampleEnum.STATUS1, v1);
    }

    @Test
    void testEnumNull() throws SQLException {
        when(resultSet.getString("status2")).thenReturn(null);

        var v1 = DatabaseUtils.readEnumValue(resultSet, "status2", SampleEnum.class);
        assertNull(v1);
    }

    public enum SampleEnum {
        STATUS0,
        STATUS1
    }
}
