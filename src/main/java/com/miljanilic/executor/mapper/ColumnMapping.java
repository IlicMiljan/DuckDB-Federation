package com.miljanilic.executor.mapper;

import com.miljanilic.sql.ast.expression.Column;

import java.util.List;

public class ColumnMapping {
    private final int[] columnIndices;
    private final int[] columnTypes;
    private final List<Column> orderedColumns;

    public ColumnMapping(int[] columnIndices, int[] columnTypes, List<Column> orderedColumns) {
        this.columnIndices = columnIndices;
        this.columnTypes = columnTypes;
        this.orderedColumns = orderedColumns;
    }

    public int getColumnIndex(int i) {
        return columnIndices[i];
    }

    public int getColumnType(int i) {
        return columnTypes[i];
    }

    public String getColumnName(int i) {
        return orderedColumns.get(i).getName();
    }

    public int getColumnCount() {
        return columnIndices.length;
    }
}
