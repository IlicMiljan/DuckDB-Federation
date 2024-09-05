package com.miljanilic.sql.ast.expression.binary;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.expression.Expression;

public class NotEqualsTo extends Comparison {
    private static final String OPERATOR = "<>";

    public NotEqualsTo(Expression left, Expression right) {
        super(left, right, OPERATOR);
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