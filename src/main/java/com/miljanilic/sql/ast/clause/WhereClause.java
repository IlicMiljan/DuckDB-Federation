package com.miljanilic.sql.ast.clause;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.expression.Expression;

public class WhereClause extends Clause {
    private final Expression condition;

    public WhereClause(Expression condition) {
        this.condition = condition;
    }

    public Expression getCondition() {
        return condition;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "WhereClause{" +
                "condition=" + condition +
                '}';
    }
}
