package com.miljanilic.sql.ast.expression.binary;

import com.miljanilic.sql.ast.expression.Expression;

public abstract class Comparison extends Binary {
    private final String operator;

    public Comparison(Expression left, Expression right, String operator) {
        super(left, right);
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return "ComparisonExpression{" +
                "left=" + super.getLeft() +
                ", right=" + super.getRight() +
                ", operator='" + operator + '\'' +
                '}';
    }
}
