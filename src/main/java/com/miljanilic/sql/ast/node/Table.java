package com.miljanilic.sql.ast.node;

import com.miljanilic.catalog.data.Schema;
import com.miljanilic.sql.ast.ASTVisitor;

import java.util.Objects;

public class Table extends From {
    private final String name;
    private final String alias;

    public Table(Schema schema, com.miljanilic.catalog.data.Table table, String name, String alias) {
        super(schema, table);
        this.name = name;
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public <T, S> T accept(ASTVisitor<T, S> visitor, S context) {
        return visitor.visit(this, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Table table)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(name, table.name) && Objects.equals(alias, table.alias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, alias);
    }

    public String toString() {
        return (schema != null ? schema.getName() + "." : "") + name + (alias != null ? " " + alias : "");
    }
}
