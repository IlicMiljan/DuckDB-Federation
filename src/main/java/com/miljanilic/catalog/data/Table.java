package com.miljanilic.catalog.data;

import java.util.List;
import java.util.stream.Collectors;

public class Table {
    private String name;
    private TableType type;
    private List<Column> columns;  // Add columns field
    private String sql;


    public Table() {}

    public Table(String name, TableType type, String sql) {
        this.name = name;
        this.type = type;
        this.sql = sql;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TableType getType() {
        return type;
    }

    public void setType(TableType type) {
        this.type = type;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public enum TableType {
        TABLE,
        VIEW
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Table: ").append(name).append(" (").append(type).append(")\n")
                .append("SQL: ").append(sql).append("\n")
                .append("Columns: ");

        if (columns != null && !columns.isEmpty()) {
            sb.append(columns.stream()
                    .map(Column::toString)
                    .collect(Collectors.joining(", ")));
        } else {
            sb.append("No columns");
        }

        return sb.toString();
    }
}
