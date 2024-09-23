package com.miljanilic.executor;

import com.google.inject.Inject;
import com.miljanilic.catalog.data.Schema;
import com.miljanilic.sql.ast.statement.SelectStatement;
import com.miljanilic.sql.deparser.SqlDeParserResolver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.function.BiConsumer;

public class SchemaQueryExecutor {
    private final SqlDeParserResolver sqlDeParserResolver;
    private final DatabaseConnectionManager databaseConnectionManager;

    @Inject
    public SchemaQueryExecutor(
            SqlDeParserResolver sqlDeParserResolver,
            DatabaseConnectionManager databaseConnectionManager
    ) {
        this.sqlDeParserResolver = sqlDeParserResolver;
        this.databaseConnectionManager = databaseConnectionManager;
    }

    public void execute(Schema schema, SelectStatement query, BiConsumer<SelectStatement, ResultSet> resultSetConsumer) {
        String sql = this.sqlDeParserResolver.resolve(schema.getDataSource().getDialect()).deparse(query);

        try (
                Connection conn = databaseConnectionManager.getConnection(schema);
                Statement statement = databaseConnectionManager.createStatement(conn);
                ResultSet resultSet = statement.executeQuery(sql)
        ) {
            resultSetConsumer.accept(query, resultSet);
        } catch (Exception e) {
            throw new SQLExecutionException("Error executing SQL query", e);
        }
    }
}
