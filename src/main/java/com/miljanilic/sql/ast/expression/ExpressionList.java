package com.miljanilic.sql.ast.expression;

import com.miljanilic.sql.ast.ASTVisitor;

import java.util.List;

public class ExpressionList extends Expression {
    private final List<Expression> expressions;

    public ExpressionList(List<Expression> expressions) {
        this.expressions = expressions;
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

    @Override
    public <T, S> T accept(ASTVisitor<T, S> visitor, S context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return "ExpressionList{" +
                "expressions=" + expressions +
                '}';
    }
}
