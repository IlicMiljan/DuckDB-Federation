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

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "LongValue{" +
                "value=" + value +
                '}';
    }
}
