package com.miljanilic.catalog.data;

import java.util.ArrayList;
import java.util.List;

public class Schema {
    private String name;
    private DataSource dataSource;
    private List<Table> tables;

    public Schema() {
        this.tables = new ArrayList<>();
    }

    public Schema(String name, DataSource dataSource) {
        this.name = name;
        this.dataSource = dataSource;
        this.tables = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    @Override
    public String toString() {
        return "Schema{" +
                "name='" + name + '\'' +
                ", dataSource=" + dataSource +
                ", schemaTableList=" + tables +
                '}';
    }
}
