package com.miljanilic.executor.table;

import com.miljanilic.executor.SQLExecutionException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DuckDbTemporaryTableCreator {

    public void create(Connection connection, TemporaryTable temporaryTable) {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(temporaryTable.toSql());
        } catch (SQLException e) {
            throw new SQLExecutionException("Failed creating temporary table: " + temporaryTable.getName(), e);
        }
    }
}
