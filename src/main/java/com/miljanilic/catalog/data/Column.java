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

    public enum ColumnType {
        INTEGER,
        VARCHAR,
        BOOLEAN,
        DATE,
        DECIMAL
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(name).append(" (").append(type).append(")")
                .toString();
    }
}
