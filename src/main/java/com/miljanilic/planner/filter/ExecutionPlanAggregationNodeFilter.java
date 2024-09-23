package com.miljanilic.planner.filter;

import com.miljanilic.planner.node.*;
import com.miljanilic.sql.ast.node.Table;

import java.util.SortedSet;
import java.util.TreeSet;

public class ExecutionPlanAggregationNodeFilter extends ExecutionPlanFilterAdapter implements ExecutionPlanFilter {

    public PlanNode filter(PlanNode rootNode) {
        return rootNode.accept(this, null);
    }

    @Override
    public PlanNode visit(ScanNode node, PlanNode context) {
        if (node instanceof RemoteScanNode) {
            return node;
        }

        return context;
    }

    @Override
    public PlanNode visit(FilterNode node, PlanNode context) {
        if (!(node instanceof RemoteFilterNode)) {
            PlanNode child = visitChildren(node.getChildren(), context);

            if (child != null) {
                FilterNode newNode = new FilterNode((node).getCondition());
                newNode.addChild(child);
                return newNode;
            }
        }

        return visitChildren(node.getChildren(), context);
    }

    @Override
    public PlanNode visit(JoinNode node, PlanNode context) {
        if (node instanceof RemoteJoinNode remoteJoinNode) {
            PlanNode left = remoteJoinNode.getLeft().accept(this, context);
            PlanNode right = remoteJoinNode.getRight().accept(this, context);

            if (left instanceof RemoteScanNode leftScanNode && right instanceof RemoteScanNode rightScanNode) {

                SortedSet<String> tableNames = new TreeSet<>();
                tableNames.add(leftScanNode.getFrom().getSchemaTable().getName());
                tableNames.add(rightScanNode.getFrom().getSchemaTable().getName());

                return new ScanNode(
                        new Table(leftScanNode.getSchema(), null, String.join("_", tableNames), null),
                        null
                );
            }
        }

        PlanNode left = node.getLeft().accept(this, context);
        PlanNode right = node.getRight().accept(this, context);

        if (left != null && right != null) {
            return new JoinNode(left, right, node.getJoinType(), node.getCondition(), node.getAlgorithm());
        }

        return visitChildren(node.getChildren(), context);
    }

    @Override
    public PlanNode visit(ProjectNode node, PlanNode context) {
        if (!(node instanceof RemoteProjectNode)) {
            PlanNode child = visitChildren(node.getChildren(), context);

            if (child != null) {
                ProjectNode newNode = new ProjectNode(node.getSelectList());
                newNode.addChild(child);

                return newNode;
            }
        }

        return visitChildren(node.getChildren(), context);
    }

    @Override
    public PlanNode visit(AggregateNode node, PlanNode context) {
        PlanNode child = visitChildren(node.getChildren(), context);

        AggregateNode newNode = new AggregateNode(
                node.getGroupByExpressions(),
                node.getAggregateFunctions()
        );

        newNode.addChild(child);

        return newNode;
    }

    @Override
    public PlanNode visit(HavingNode node, PlanNode context) {
        PlanNode child = visitChildren(node.getChildren(), context);

        HavingNode newNode = new HavingNode(node.getCondition());

        newNode.addChild(child);

        return newNode;
    }

    @Override
    public PlanNode visit(SortNode node, PlanNode context) {
        PlanNode child = visitChildren(node.getChildren(), context);

        SortNode newNode = new SortNode(node.getOrderByList());

        newNode.addChild(child);

        return newNode;
    }

    @Override
    public PlanNode visit(LimitNode node, PlanNode context) {
        PlanNode child = visitChildren(node.getChildren(), context);

        LimitNode newNode = new LimitNode(node.getLimit(), node.getOffset());

        newNode.addChild(child);

        return newNode;
    }
}
