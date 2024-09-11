package com.miljanilic.sql.ast.expression.binary;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.expression.Expression;

import java.util.Objects;

public abstract class Binary extends Expression {
    private final Expression left;
    private final String operator;
    private final Expression right;

    public Binary(Expression left, String operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public String getOperator() {
        return operator;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public abstract <T, S> T accept(ASTVisitor<T, S> visitor, S context);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Binary binary)) return false;
        return Objects.equals(left, binary.left) && Objects.equals(operator, binary.operator) && Objects.equals(right, binary.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, operator, right);
    }

    public String toString() {
        return "(" + left + " " + operator + " " + right + ")";
    }
}
