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
    public abstract <T> T accept(ASTVisitor<T> visitor);

    @Override
    public String toString() {
        return "Binary{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
