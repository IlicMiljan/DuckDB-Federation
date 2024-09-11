package com.miljanilic.sql.ast.node;

import com.miljanilic.catalog.data.Schema;
import com.miljanilic.sql.ast.ASTVisitor;

import java.util.Objects;

public abstract class From extends Node {
    protected final Schema schema;

    protected From(Schema schema) {
        this.schema = schema;
    }

    public Schema getSchema() {
        return schema;
    }

    public Table getSchemaTable() {
        return schemaTable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof From from)) return false;
        return Objects.equals(schema, from.schema) && Objects.equals(schemaTable, from.schemaTable);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(schema);
    }

    @Override
    public abstract <T, S> T accept(ASTVisitor<T, S> visitor, S context);
}
