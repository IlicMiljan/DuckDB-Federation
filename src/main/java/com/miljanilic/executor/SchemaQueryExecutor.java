package com.miljanilic.executor;

import com.google.inject.Inject;
import com.miljanilic.catalog.data.Schema;
import com.miljanilic.sql.ast.statement.SelectStatement;
import com.miljanilic.sql.deparser.SqlDeParser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.function.BiConsumer;

public class SchemaQueryExecutor {
    private final SqlDeParser sqlDeParser;


    @Inject
    public SchemaQueryExecutor(SqlDeParser sqlDeParser) {
        this.sqlDeParser = sqlDeParser;
    }

    public void execute(Schema schema, SelectStatement query, BiConsumer<SelectStatement, ResultSet> resultSetConsumer) {
        String sql = sqlDeParser.deparse(query);

        try (
                Connection conn = DriverManager.getConnection(schema.getDataSource().getJdbcUrl(), schema.getDataSource().getJdbcUser(), schema.getDataSource().getJdbcPassword());
        ) {
            Statement statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

            // TODO: Support other databases.
            statement.setFetchSize(Integer.MIN_VALUE);  // Enable streaming in MySQL

            try (ResultSet resultSet = statement.executeQuery(sql)) {
                resultSetConsumer.accept(query, resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLExecutionException("Error executing SQL query", e);
        }
    }
}
