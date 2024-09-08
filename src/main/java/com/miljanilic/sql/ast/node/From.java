package com.miljanilic.sql.ast.node;

import com.miljanilic.catalog.data.Schema;
import com.miljanilic.sql.ast.ASTVisitor;

public abstract class From extends Node {
    protected final Schema schema;

    protected From(Schema schema) {
        this.schema = schema;
    }

    public Schema getSchema() {
        return schema;
    }

    @Override
    public abstract <T, S> T accept(ASTVisitor<T, S> visitor, S context);
}
