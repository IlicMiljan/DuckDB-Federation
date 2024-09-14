package com.miljanilic.executor;

import com.miljanilic.executor.mapper.ColumnMapping;
import org.duckdb.DuckDBAppender;
import org.duckdb.DuckDBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class DuckDbResultSetDataAppender {

    public void append(Connection duckDBConnection, String tableName, ResultSet sourceResultSet, ColumnMapping columnMapping) throws SQLExecutionException {
        try (DuckDBAppender appender = createAppender(duckDBConnection, tableName)) {
            while (sourceResultSet.next()) {
                appendRow(appender, sourceResultSet, columnMapping);
            }

            appender.flush();
        } catch (SQLException e) {
            throw new SQLExecutionException("Failed to append ResultSet data to DuckDB", e);
        }
    }

    private DuckDBAppender createAppender(Connection duckDBConnection, String tableName) throws SQLException {
        return ((DuckDBConnection) duckDBConnection).createAppender(DuckDBConnection.DEFAULT_SCHEMA, tableName);
    }

    private void appendRow(DuckDBAppender appender, ResultSet sourceResultSet, ColumnMapping columnMapping) throws SQLException {
        appender.beginRow();

        for (int i = 0; i < columnMapping.getColumnCount(); i++) {
            int resultSetColumnIndex = columnMapping.getColumnIndex(i);

            switch (columnMapping.getColumnType(i)) {
                case Types.INTEGER:
                    appender.append(sourceResultSet.getInt(resultSetColumnIndex));
                    break;
                case Types.BIGINT:
                    appender.append(sourceResultSet.getLong(resultSetColumnIndex));
                    break;
                case Types.DECIMAL:
                case Types.DOUBLE:
                    appender.append(sourceResultSet.getDouble(resultSetColumnIndex));
                    break;
                case Types.VARCHAR:
                case Types.CHAR:
                case Types.DATE:
                    appender.append(sourceResultSet.getString(resultSetColumnIndex));
                    break;
                default:
                    throw new SQLException("Unsupported column type: " + columnMapping.getColumnType(i));
            }
        }

        appender.endRow();
    }
}
