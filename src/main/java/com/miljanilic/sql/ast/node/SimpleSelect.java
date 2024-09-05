package com.miljanilic.sql.ast.node;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.expression.Expression;

public class SimpleSelect extends Select {
    private final Expression expression;
    private final String alias;

    public SimpleSelect(Expression expression, String alias) {
        this.expression = expression;
        this.alias = alias;
    }

    public Expression getExpression() {
        return expression;
    }

    public String getAlias() {
        return alias;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "SimpleSelectNode{" +
                "expression=" + expression +
                ", alias='" + alias + '\'' +
                '}';
    }
}
