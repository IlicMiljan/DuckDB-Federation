package com.miljanilic.catalog.data;

public class DataSource {
    private String jdbcUrl;
    private String jdbcUser;
    private String jdbcPassword;
    private String jdbcDriver;

    public DataSource() {}

    public DataSource(String jdbcUrl, String jdbcUser, String jdbcPassword, String jdbcDriver) {
        this.jdbcUrl = jdbcUrl;
        this.jdbcUser = jdbcUser;
        this.jdbcPassword = jdbcPassword;
        this.jdbcDriver = jdbcDriver;
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

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("JDBC URL: ").append(jdbcUrl).append("\n")
                .append("Driver: ").append(jdbcDriver)
                .toString();
    }
}
