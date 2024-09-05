package com.miljanilic.sql.ast.node;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.expression.Expression;

public class SimpleJoin extends Join {
    private final Join.JoinType joinType;
    private final From from;
    private final Expression condition;

    public SimpleJoin(Join.JoinType joinType, From from, Expression condition) {
        this.joinType = joinType;
        this.from = from;
        this.condition = condition;
    }

    public JoinType getJoinType() {
        return joinType;
    }

    public From getFrom() {
        return from;
    }

    public Expression getConditions() {
        return condition;
    }

    public <T, S> T accept(ASTVisitor<T, S> visitor, S context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return "SimpleJoinNode{" +
                "joinType=" + joinType +
                ", from=" + from +
                ", condition=" + condition +
                '}';
    }
}
