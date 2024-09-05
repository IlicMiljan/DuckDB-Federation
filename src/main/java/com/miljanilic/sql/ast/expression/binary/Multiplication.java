package com.miljanilic.sql.ast.expression.binary;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.expression.Expression;

public class Multiplication extends Binary {
    private static final String OPERATOR = "*";

    public Multiplication(Expression left, Expression right) {
        super(left, right);
    }

    public String getOperator() {
        return OPERATOR;
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