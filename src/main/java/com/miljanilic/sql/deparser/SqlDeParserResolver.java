package com.miljanilic.sql.deparser;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.SqlDialect;

@Singleton
public class SqlDeParserResolver {
    private final SimpleJSqlDeParser simpleJSqlDeParser;
    private final TableColumnNameJSqlDeParser tableColumnNameJSqlDeParser;
    private final FullyQualifiedColumnNameJSqlDeParser fullyQualifiedColumnNameDeParser;

    @Inject
    public SqlDeParserResolver(
            SimpleJSqlDeParser simpleJSqlDeParser,
            TableColumnNameJSqlDeParser tableColumnNameJSqlDeParser,
            FullyQualifiedColumnNameJSqlDeParser fullyQualifiedColumnNameDeParser
    ) {
        this.simpleJSqlDeParser = simpleJSqlDeParser;
        this.tableColumnNameJSqlDeParser = tableColumnNameJSqlDeParser;
        this.fullyQualifiedColumnNameDeParser = fullyQualifiedColumnNameDeParser;
    }

    public SqlDeParser resolve(SqlDialect sqlDialect) {
        return switch (sqlDialect) {
            case DEFAULT, MYSQL, POSTGRESQL -> fullyQualifiedColumnNameDeParser;
            case DUCKDB -> tableColumnNameJSqlDeParser;
            case CASSANDRA -> simpleJSqlDeParser;
        };
    }
}
