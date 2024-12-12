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

    /**
     * Check the column exist in ResultSet
     * @param resultSet ResultSet fto check
     * @param columnName The column name
     * @return `true` if column exist. Otherwise `false`
     */
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

    /**
     * Read field as Instant. It's useful for such RDBMS as PostgreSQL.
     * @param rs ResultSet
     * @param fieldName Field name
     * @return Instant object or `null`
     * @throws SQLException on any SQL exception
     */
    public static Instant readAsInstant(ResultSet rs, String fieldName) throws SQLException {
        OffsetDateTime tmpDate = rs.getObject(fieldName, OffsetDateTime.class);
        if (tmpDate == null)
            return null;
        return tmpDate.toInstant();
    }

    /**
     * Converts a LocalDateTime instance to an Instant in UTC.
     *
     * @param value the LocalDateTime object to convert, may be null
     * @return the corresponding Instant object in UTC, or null if the input was null
     */
    public static Instant asInstant(LocalDateTime value) {
        if (value == null)
            return null;
        return value.toInstant(ZoneOffset.UTC);
    }

    /**
     * Reads a value from a ResultSet column and converts it to the corresponding Enum constant.
     *
     * @param rs       the ResultSet object to read the value from
     * @param fieldName the name of the column in the ResultSet
     * @param enumClass the class of the Enum type to which the value should be mapped
     * @param <E>       the Enum type
     * @return the Enum constant that corresponds to the value read from the column,
     *         or null if the column value is null
     * @throws SQLException if an SQL exception occurs while reading the ResultSet
     */
    public static <E extends Enum<E>> E readEnumValue(ResultSet rs, String fieldName, Class<E> enumClass) throws SQLException {
        final String stringValue = rs.getString(fieldName);
        if (stringValue == null)
            return null;
        return Enum.valueOf(enumClass, stringValue);
    }

}
