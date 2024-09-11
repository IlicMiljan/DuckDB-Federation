package com.miljanilic.sql.ast.expression;

import com.miljanilic.sql.ast.ASTVisitor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExpressionList that)) return false;
        return Objects.equals(expressions, that.expressions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(expressions);
    }

    @Override
    public String toString() {
        return expressions.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }
}
