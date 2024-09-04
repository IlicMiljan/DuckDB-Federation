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
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "MultiExpressionList{" +
                "expressions=" + expressions +
                '}';
    }
}
