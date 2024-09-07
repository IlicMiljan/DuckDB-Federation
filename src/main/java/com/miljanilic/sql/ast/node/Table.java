package com.miljanilic.sql.ast.node;

import com.miljanilic.sql.ast.ASTVisitor;

public class Table extends From {
    private final String schema;
    private final String name;
    private final String alias;

    public Table(String schema, String name, String alias) {
        this.schema = schema;
        this.name = name;
        this.alias = alias;
    }

    public String getSchema() {
        return schema;
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

    public String toString() {
        return (schema != null ? schema + "." : "") + name + (alias != null ? " " + alias : "");
    }
}
