package com.miljanilic.executor.table;

import com.miljanilic.executor.SQLExecutionException;
import com.miljanilic.sql.ast.expression.Column;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class DuckDbTemporaryTableCreator {

    public void create(Connection connection, TemporaryTable temporaryTable) {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(temporaryTable.toSql());
        } catch (SQLException e) {
            throw new SQLExecutionException("Failed creating temporary table: " + temporaryTable.getName(), e);
        }
    }
}
