package com.miljanilic.sql.ast.expression;

import com.miljanilic.sql.ast.ASTVisitor;

public class LongValue extends Expression {
    private final Long value;

    public LongValue(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public <T, S> T accept(ASTVisitor<T, S> visitor, S context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
