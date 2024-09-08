package com.miljanilic.planner.rule;

import com.miljanilic.planner.ExecutionPlanVisitor;
import com.miljanilic.planner.node.*;

import java.util.ArrayList;
import java.util.List;

public class ExecutionPlannerRuleAdapter implements ExecutionPlanVisitor<PlanNode, PlanNode>  {
    @Override
    public PlanNode visit(ScanNode node, PlanNode context) {
        node.setChildren(visitChildren(node.getChildren(), context));
        return node;
    }

    @Override
    public PlanNode visit(FilterNode node, PlanNode context) {
        node.setChildren(visitChildren(node.getChildren(), context));
        return node;
    }

    @Override
    public PlanNode visit(JoinNode node, PlanNode context) {
        node.setChildren(visitChildren(node.getChildren(), context));
        return node;
    }

    @Override
    public PlanNode visit(ProjectNode node, PlanNode context) {
        node.setChildren(visitChildren(node.getChildren(), context));
        return node;
    }

    @Override
    public PlanNode visit(AggregateNode node, PlanNode context) {
        node.setChildren(visitChildren(node.getChildren(), context));
        return node;
    }

    @Override
    public PlanNode visit(SortNode node, PlanNode context) {
        node.setChildren(visitChildren(node.getChildren(), context));
        return node;
    }

    @Override
    public PlanNode visit(LimitNode node, PlanNode context) {
        node.setChildren(visitChildren(node.getChildren(), context));
        return node;
    }

    @Override
    public PlanNode visit(HavingNode node, PlanNode context) {
        node.setChildren(visitChildren(node.getChildren(), context));
        return node;
    }

    protected List<PlanNode> visitChildren(List<PlanNode> children, PlanNode context) {
        List<PlanNode> visitedChildren = new ArrayList<>();

        for (PlanNode child : children) {
            visitedChildren.add(child.accept(this, context));
        }

        return visitedChildren;
    }
}
