package com.miljanilic.planner.node;

import com.miljanilic.planner.ExecutionPlanVisitor;
import com.miljanilic.sql.ast.expression.Expression;

public class LimitNode extends PlanNode {
    private final Expression limit;
    private final Expression offset;

    public LimitNode(Expression limit, Expression offset) {
        this.limit = limit;
        this.offset = offset;
    }

    public Expression getLimit() {
        return limit;
    }

    public Expression getOffset() {
        return offset;
    }

    @Override
    public <T, S> T accept(ExecutionPlanVisitor<T, S> visitor, S context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return "Limit: " + limit + (offset != null ? " Offset: " + offset : "");
    }
}
