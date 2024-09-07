package com.miljanilic.sql.ast.expression.binary;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.expression.Expression;

public class EqualsTo extends Comparison {
    private static final String OPERATOR = "=";

    public EqualsTo(Expression left, Expression right) {
        super(left, OPERATOR, right);
    }

    @Override
    public <T, S> T accept(ASTVisitor<T, S> visitor, S context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
