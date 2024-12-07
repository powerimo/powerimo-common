package org.powerimo.common.resstrings;

import lombok.Getter;
import lombok.Setter;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public DatabaseSimpleSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

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

    @Override
    public void addResourceRecord(ResourceRecord record) {
        throw new ResStringException("Method is not supported");
    }

    @Override
    public boolean isExists(String code) {
        return getResourceRecord(code) != null;
    }
}
