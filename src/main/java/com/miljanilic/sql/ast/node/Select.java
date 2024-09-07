package com.miljanilic.sql.ast.node;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.expression.Expression;

public class Select extends Node {
    private final Expression expression;
    private final String alias;

    public Select(Expression expression, String alias) {
        this.expression = expression;
        this.alias = alias;
    }

    public Expression getExpression() {
        return expression;
    }

    public String getAlias() {
        return alias;
    }

    public <T, S> T accept(ASTVisitor<T, S> visitor, S context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return expression + (alias != null ? " AS " + alias : "");
    }
}
