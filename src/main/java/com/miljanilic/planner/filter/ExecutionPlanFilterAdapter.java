package com.miljanilic.planner.filter;

import com.miljanilic.planner.ExecutionPlanVisitor;
import com.miljanilic.planner.node.*;

import java.util.List;

public class ExecutionPlanFilterAdapter implements ExecutionPlanVisitor<PlanNode, PlanNode> {

    @Override
    public PlanNode visit(ScanNode node, PlanNode context) {
        return visitChildren(node.getChildren(), context);
    }

    @Override
    public PlanNode visit(FilterNode node, PlanNode context) {
        return visitChildren(node.getChildren(), context);
    }

    @Override
    public PlanNode visit(JoinNode node, PlanNode context) {
        return visitChildren(node.getChildren(), context);
    }

    @Override
    public PlanNode visit(ProjectNode node, PlanNode context) {
        return visitChildren(node.getChildren(), context);
    }

    @Override
    public PlanNode visit(AggregateNode node, PlanNode context) {
        return visitChildren(node.getChildren(), context);
    }

    @Override
    public PlanNode visit(SortNode node, PlanNode context) {
        return visitChildren(node.getChildren(), context);
    }

    @Override
    public PlanNode visit(LimitNode node, PlanNode context) {
        return visitChildren(node.getChildren(), context);
    }

    @Override
    public PlanNode visit(HavingNode node, PlanNode context) {
        return visitChildren(node.getChildren(), context);
    }

    protected PlanNode visitChildren(List<PlanNode> children, PlanNode context) {
        PlanNode result = context;

        for (PlanNode child : children) {
            result = child.accept(this, result);
        }

        return result;
    }
}
