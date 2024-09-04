package com.miljanilic.sql.ast.expression.binary;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.expression.Expression;

public class EqualsTo extends Comparison {
    private static final String OPERATOR = "=";

    public EqualsTo(Expression left, Expression right) {
        super(left, right, OPERATOR);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
