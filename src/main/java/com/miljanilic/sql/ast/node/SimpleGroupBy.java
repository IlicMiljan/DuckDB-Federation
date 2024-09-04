package com.miljanilic.sql.ast.node;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.expression.Expression;

public class SimpleGroupBy extends GroupBy {
    private final Expression expression;

    public SimpleGroupBy(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "SimpleGroupByNode{" +
                "expression=" + expression +
                '}';
    }
}
