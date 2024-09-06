package com.miljanilic.catalog.data;

public class Column {
    private String name;
    private ColumnType type;

    public Column() {}

    public Column(String name, ColumnType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ColumnType getType() {
        return type;
    }

    public void setType(ColumnType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }

    public enum ColumnType {
        INTEGER,
        VARCHAR,
        BOOLEAN,
        DATE,
        DECIMAL
    }
}

