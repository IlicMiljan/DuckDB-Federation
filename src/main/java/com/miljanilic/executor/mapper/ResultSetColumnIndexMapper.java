package com.miljanilic.executor.mapper;

import com.miljanilic.sql.ast.expression.Column;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public class ResultSetColumnIndexMapper {

    public ColumnMapping map(ResultSetMetaData metaData, Set<Column> queryColumns) throws SQLException {
        Map<String, ColumnInfo> resultSetColumnInfo = extractResultSetColumnInfo(metaData);
        return createColumnMapping(queryColumns, resultSetColumnInfo);
    }

    private Map<String, ColumnInfo> extractResultSetColumnInfo(ResultSetMetaData metaData) throws SQLException {
        Map<String, ColumnInfo> columnInfo = new HashMap<>();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i).toLowerCase();
            int columnType = metaData.getColumnType(i);
            columnInfo.put(columnName, new ColumnInfo(i, columnType));
        }
        return columnInfo;
    }

    private ColumnMapping createColumnMapping(Set<Column> queryColumns, Map<String, ColumnInfo> resultSetColumnInfo) {
        List<Column> orderedColumns = new ArrayList<>(queryColumns);
        int[] columnIndices = new int[orderedColumns.size()];
        int[] columnTypes = new int[orderedColumns.size()];

        for (int i = 0; i < orderedColumns.size(); i++) {
            Column column = orderedColumns.get(i);
            String columnName = column.getName().toLowerCase();
            ColumnInfo info = resultSetColumnInfo.get(columnName);

            if (info == null) {
                throw new ExecutionMappingException("Column '" + columnName + "' not found in the result set.");
            }

            columnIndices[i] = info.index;
            columnTypes[i] = info.type;
        }

        return new ColumnMapping(columnIndices, columnTypes, orderedColumns);
    }

    private record ColumnInfo(int index, int type) { }
}
