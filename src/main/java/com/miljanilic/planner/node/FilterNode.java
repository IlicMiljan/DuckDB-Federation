package com.miljanilic.planner.node;

import com.miljanilic.planner.ExecutionPlanVisitor;
import com.miljanilic.sql.ast.expression.Expression;

public class FilterNode extends PlanNode {
    private final Expression condition;

    public FilterNode(Expression condition) {
        this.condition = condition;
    }

    public Expression getCondition() {
        return condition;
    }

    @Override
    public <T, S> T accept(ExecutionPlanVisitor<T, S> visitor, S context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return "Filter: " + condition;
    }
}
