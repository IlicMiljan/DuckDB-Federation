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
    public <T, S> T accept(ASTVisitor<T, S> visitor, S context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return "SimpleOrderByNode{" +
                "expression=" + expression +
                ", isAscending=" + isAscending +
                '}';
    }
}
