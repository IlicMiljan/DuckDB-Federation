package com.miljanilic.sql.ast.expression;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.node.From;

public class Column extends Expression {
    private final String name;
    private final From from;

    public Column(String name, From from) {
        this.name = name;
        this.from = from;
    }

    public String getName() {
        return name;
    }

    public From getFrom() {
        return from;
    }

    @Override
    public <T, S> T accept(ASTVisitor<T, S> visitor, S context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return (from != null ? from + "." : "") + name;
    }
}
