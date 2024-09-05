package com.miljanilic.sql.ast.expression.binary;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.expression.Expression;

public abstract class Binary extends Expression {
    private final Expression left;
    private final Expression right;

    public Binary(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public abstract <T, S> T accept(ASTVisitor<T, S> visitor, S context);

    @Override
    public String toString() {
        return "Binary{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
