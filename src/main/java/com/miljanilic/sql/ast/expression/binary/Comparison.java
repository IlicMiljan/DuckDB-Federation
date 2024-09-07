package com.miljanilic.sql.ast.expression.binary;

import com.miljanilic.sql.ast.expression.Expression;

public abstract class Comparison extends Binary {
    public Comparison(Expression left, String operator, Expression right) {
        super(left, operator, right);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
