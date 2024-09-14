package com.miljanilic.executor.table;

import java.util.List;
import java.util.stream.Collectors;

public class TemporaryTable {
    private final String name;
    private final List<ColumnDefinition> columns;

    public TemporaryTable(String name, List<ColumnDefinition> columns) {
        this.name = name;
        this.columns = columns;
    }

    public String getName() {
        return name;
    }

    public List<ColumnDefinition> getColumns() {
        return columns;
    }

    public String toSql() {
        String columnDefinitions = columns.stream()
                .map(ColumnDefinition::toSql)
                .collect(Collectors.joining(", "));

        return String.format("CREATE TEMPORARY TABLE %s (%s)", name, columnDefinitions);
    }
}
