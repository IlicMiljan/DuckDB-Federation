package com.miljanilic.sql.ast.node;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.expression.Expression;

public class SimpleOrderBy extends OrderBy {
    private final Expression expression;
    private final boolean isAscending;

    public SimpleOrderBy(Expression expression, boolean isAscending) {
        this.expression = expression;
        this.isAscending = isAscending;
    }

    public Expression getExpression() {
        return expression;
    }

    public boolean isAscending() {
        return isAscending;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "SimpleOrderByNode{" +
                "expression=" + expression +
                ", isAscending=" + isAscending +
                '}';
    }
}
