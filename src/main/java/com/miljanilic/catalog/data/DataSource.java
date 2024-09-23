package com.miljanilic.catalog.data;

import com.miljanilic.sql.SqlDialect;

public class DataSource {
    private String jdbcUrl;
    private String jdbcUser;
    private String jdbcPassword;
    private SqlDialect dialect;

    public DataSource() {}

    public DataSource(String jdbcUrl, String jdbcUser, String jdbcPassword, SqlDialect dialect) {
        this.jdbcUrl = jdbcUrl;
        this.jdbcUser = jdbcUser;
        this.jdbcPassword = jdbcPassword;
        this.dialect = dialect;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcUser() {
        return jdbcUser;
    }

    public void setJdbcUser(String jdbcUser) {
        this.jdbcUser = jdbcUser;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    public SqlDialect getDialect() {
        return dialect;
    }

    public void setDialect(SqlDialect dialect) {
        this.dialect = dialect;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("JDBC URL: ").append(jdbcUrl).append("\n")
                .append("Dialect: ").append(dialect)
                .toString();
    }
}
