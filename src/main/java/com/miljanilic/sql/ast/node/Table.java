package com.miljanilic.sql.ast.node;

import com.miljanilic.catalog.data.Schema;
import com.miljanilic.sql.ast.ASTVisitor;

public class Table extends From {
    private final String name;
    private final String alias;

    public Table(Schema schema, String name, String alias) {
        super(schema);
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

    public String toString() {
        return (schema != null ? schema.getName() + "." : "") + name + (alias != null ? " " + alias : "");
    }
}
