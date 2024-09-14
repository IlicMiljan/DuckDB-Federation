package com.miljanilic.executor.table;

import com.miljanilic.sql.ast.expression.Column;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class DuckDbTemporaryTableDefinitionGenerator {

    public TemporaryTable generate(Set<Column> columns) {
        String tableName = generateTableName(columns);
        List<ColumnDefinition> columnDefinitions = generateColumnDefinitions(columns);

        return new TemporaryTable(tableName, columnDefinitions);
    }

    private String generateTableName(Set<Column> columns) {
        SortedSet<String> tableNames = columns.stream()
                .map(column -> column.getFrom().getSchemaTable().getName())
                .collect(Collectors.toCollection(TreeSet::new));

        return String.join("_", tableNames);
    }

    private List<ColumnDefinition> generateColumnDefinitions(Set<Column> columns) {
        return columns.stream()
                .map(column -> {
                    com.miljanilic.catalog.data.Column columnData = column.getFrom().getSchemaTable().getColumns().stream()
                            .filter(c -> c.getName().equals(column.getName()))
                            .findFirst()
                            .orElseThrow(() -> new IllegalStateException("No matching column data found for " + column.getName()));

                    return new ColumnDefinition(column.getName(), columnData.getType());
                })
                .collect(Collectors.toList());
    }
}
