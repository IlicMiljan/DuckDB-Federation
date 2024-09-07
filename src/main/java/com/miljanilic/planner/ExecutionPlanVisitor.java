package com.miljanilic.planner;

import com.miljanilic.planner.node.*;

public interface ExecutionPlanVisitor<T, S> {
    T visit(ScanNode node, S context);
    T visit(FilterNode node, S context);
    T visit(JoinNode node, S context);
    T visit(ProjectNode node, S context);
    T visit(AggregateNode node, S context);
    T visit(SortNode node, S context);
    T visit(LimitNode node, S context);
    T visit(HavingNode node, S context);
}
