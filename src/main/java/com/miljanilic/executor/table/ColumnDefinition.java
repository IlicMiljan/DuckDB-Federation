package com.miljanilic.executor.table;

import com.miljanilic.catalog.data.Column;

public class ColumnDefinition {
    private final String name;
    private final Column.ColumnType type;

    public ColumnDefinition(String name, Column.ColumnType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Column.ColumnType getType() {
        return type;
    }

    public String toSql() {
        return name + " " + type;
    }
}
