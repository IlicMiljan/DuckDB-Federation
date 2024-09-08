package com.miljanilic.planner.node;

import com.miljanilic.planner.ExecutionPlanVisitor;
import com.miljanilic.sql.ast.node.OrderBy;

import java.util.List;
import java.util.stream.Collectors;

public class SortNode extends PlanNode {
    private final List<OrderBy> orderByList;

    public SortNode(List<OrderBy> orderByList) {
        this.orderByList = orderByList;
    }

    public List<OrderBy> getOrderByList() {
        return orderByList;
    }

    @Override
    public <T, S> T accept(ExecutionPlanVisitor<T, S> visitor, S context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return "Sort: " + orderByList.stream()
                .map(orderBy -> orderBy.getExpression() + (orderBy.isAscending() ? " ASC" : " DESC"))
                .collect(Collectors.joining(", "));
    }
}
