package com.miljanilic.sql.ast.expression;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.node.From;

import java.util.Objects;

public class Column extends Expression {
    private final String name;
    private From from;

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

    public void setFrom(From from) {
        this.from = from;
    }

    @Override
    public <T, S> T accept(ASTVisitor<T, S> visitor, S context) {
        return visitor.visit(this, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Column column)) return false;
        return Objects.equals(name, column.name) && Objects.equals(from, column.from);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, from);
    }

    @Override
    public String toString() {
        return (from != null ? from + "." : "") + name;
    }
}
