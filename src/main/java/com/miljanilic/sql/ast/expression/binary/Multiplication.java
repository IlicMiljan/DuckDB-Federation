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
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
