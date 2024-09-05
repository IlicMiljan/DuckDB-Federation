package com.miljanilic.sql.ast.expression;

import com.miljanilic.sql.ast.ASTVisitor;

public class StringValue extends Expression {
    public final String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public <T, S> T accept(ASTVisitor<T, S> visitor, S context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return "StringValue{" +
                "value='" + value + '\'' +
                '}';
    }
}
