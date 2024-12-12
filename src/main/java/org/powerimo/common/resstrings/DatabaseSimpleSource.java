package org.powerimo.common.resstrings;

import lombok.Getter;
import lombok.Setter;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DatabaseSimpleSource is an implementation of the ResourceStringSource interface
 * that retrieves resource strings from a database. This class is designed to query
 * a database for resource records associated with a specific code and supports
 * localization by storing text in multiple languages. The class also supports
 * retrieval of associated HTTP status codes if defined.
 * <p>
 * The primary functionality includes:
 * - Fetching a resource record from the database based on a unique code.
 * - Verifying the existence of a resource record in the database.
 * <p>
 * Field variables allow customization of the database table name and column
 * names used for querying the resource data.
 * <p>
 * This class does not support runtime modifications of resource records and
 * throws an exception if attempts are made to add records.
 * <p>
 * Instances of DatabaseSimpleSource require a valid DataSource object to
 * establish database connections. If the DataSource is null or unavailable,
 * operations will fail with a ResStringException.
 * <p>
 * Usage considerations:
 * - Ensure the database is properly configured with the expected schema.
 * - Customize table and field names as necessary using the available
 *   string fields in this class.
 */
@Getter
@Setter
public class DatabaseSimpleSource implements ResourceStringSource {
    private final DataSource dataSource;
    private String fieldCode = "code";
    private String fieldTextRu = "ru_text";
    private String fieldTextEn = "en_text";
    private String fieldTextDe = "de_text";
    private String fieldHttpCode = "http_code";
    private String tableName = "res_string";

    /**
     * Constructs a new instance of DatabaseSimpleSource with the given DataSource.
     *
     * @param dataSource the DataSource used to interact with the database for managing
     *                   resource records.
     */
    public DatabaseSimpleSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Retrieves a ResourceRecord based on the provided resource code.
     * This method accesses the underlying datasource to query the corresponding resource record
     * and returns it if found. If the datasource is not set or an exception occurs during the
     * query execution, a ResStringException is thrown.
     *
     * @param code the unique identifier of the resource record to be retrieved
     * @return the ResourceRecord associated with the provided code, or null if no matching record is found
     * @throws ResStringException if the datasource is not configured or if an error occurs during the query
     */
    @Override
    public ResourceRecord getResourceRecord(String code) {
        if (dataSource == null) {
            throw new ResStringException("DataSource is not set for DatabaseSource. Resource string can't be extracted");
        }

        try (var cn = dataSource.getConnection()) {
            var query = "select * from " + tableName + " where " + fieldCode + "=?";
            var ps = cn.prepareStatement(query);
            ps.setString(1, code);
            var rs = ps.executeQuery();
            if (rs.next()) {
                return readRecord(rs);
            }
            return null;
        } catch (Exception ex) {
            throw new ResStringException("Exception og getting resource string", ex);
        }
    }

    /**
     * Reads a resource record from the provided ResultSet and maps it to a ResourceRecord object.
     *
     * @param rs the ResultSet object containing the data for a resource record
     * @return a ResourceRecord object containing the data read from the ResultSet
     * @throws SQLException if an SQL error occurs while accessing the ResultSet
     */
    protected ResourceRecord readRecord(ResultSet rs) throws SQLException {
        ResourceRecord record = new ResourceRecord();
        record.setCode(rs.getString(fieldCode));
        if (fieldHttpCode != null) {
            record.setHttpCode(rs.getObject(fieldHttpCode, Integer.class));
        }
        if (fieldTextEn != null)
            record.addText("en", rs.getString(fieldTextEn));
        if (fieldTextRu != null)
            record.addText("ru", rs.getString(fieldTextRu));
        if (fieldTextDe != null)
            record.addText("de", rs.getString(fieldTextDe));
        return record;
    }

    /**
     * Adds a new resource record to the resource string source.
     * This implementation in DatabaseSimpleSource throws an UnsupportedOperationException.
     *
     * @param record the resource record to add
     */
    @Override
    public void addResourceRecord(ResourceRecord record) {
        throw new ResStringException("Method is not supported");
    }

    /**
     * Checks whether a resource record with the specified code exists in the data source.
     *
     * @param code the unique code identifying the resource record to check
     * @return true if a resource record with the specified code exists, false otherwise
     */
    @Override
    public boolean isExists(String code) {
        return getResourceRecord(code) != null;
    }
}
