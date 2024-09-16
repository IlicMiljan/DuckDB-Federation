package com.miljanilic.executor;

import com.miljanilic.catalog.data.Schema;

import java.sql.*;

public class DatabaseConnectionManager {
    public Connection getConnection(Schema schema) throws SQLException {
        return DriverManager.getConnection(
                schema.getDataSource().getJdbcUrl(),
                schema.getDataSource().getJdbcUser(),
                schema.getDataSource().getJdbcPassword()
        );
    }

    public Statement createStatement(Connection conn) throws SQLException {
        Statement statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        setFetchSize(conn, statement);

        return statement;
    }

    private void setFetchSize(Connection conn, Statement statement) throws SQLException {
        String dbProductName = conn.getMetaData().getDatabaseProductName().toUpperCase();
        if (dbProductName.contains("MYSQL")) {
            statement.setFetchSize(Integer.MIN_VALUE);  // Enable streaming for MySQL
        } else {
            statement.setFetchSize(5000);  // Default fetch size for other databases
        }
    }
}
