package org.powerimo.common.utils;

import org.powerimo.common.exception.PowerimoRuntimeException;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class DatabaseUtils {

    public static boolean isColumnExists(ResultSet resultSet, String columnName) {
        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columns = rsmd.getColumnCount();
            for (int x = 1; x <= columns; x++) {
                if (columnName.equals(rsmd.getColumnName(x))) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new PowerimoRuntimeException("Exception in checking resultSet for existing column", e);
        }
        return false;
    }

    public static boolean isFieldExists(ResultSet rs, String fieldName) {
        try {
            return rs.findColumn(fieldName) >= 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public static Instant readAsInstant(ResultSet rs, String fieldName) throws SQLException {
        OffsetDateTime tmpDate = rs.getObject(fieldName, OffsetDateTime.class);
        if (tmpDate == null)
            return null;
        return tmpDate.toInstant();
    }

    public static Instant asInstant(LocalDateTime value) {
        if (value == null)
            return null;
        return value.toInstant(ZoneOffset.UTC);
    }

    public static <E extends Enum<E>> E readEnumValue(ResultSet rs, String fieldName, Class<E> enumClass) throws SQLException {
        final String stringValue = rs.getString(fieldName);
        if (stringValue == null)
            return null;
        return Enum.valueOf(enumClass, stringValue);
    }

}
