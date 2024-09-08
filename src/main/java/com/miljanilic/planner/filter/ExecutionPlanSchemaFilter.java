package com.miljanilic.planner.filter;

import com.miljanilic.catalog.data.Schema;
import com.miljanilic.planner.node.*;

public class ExecutionPlanSchemaFilter extends ExecutionPlanFilterAdapter implements ExecutionPlanFilter {

    private final Schema targetSchema;

    public ExecutionPlanSchemaFilter(Schema targetSchema) {
        this.targetSchema = targetSchema;
    }

    public PlanNode filter(PlanNode rootNode) {
        return rootNode.accept(this, null);
    }

    @Override
    public PlanNode visit(ScanNode node, PlanNode context) {
        if (node instanceof RemoteScanNode && ((RemoteScanNode) node).getSchema().equals(targetSchema)) {
            return node;
        }

        return context;
    }

    @Override
    public PlanNode visit(FilterNode node, PlanNode context) {
        if (node instanceof RemoteFilterNode && ((RemoteFilterNode) node).getSchema().equals(targetSchema)) {
            PlanNode child = visitChildren(node.getChildren(), context);

            if (child != null) {
                RemoteFilterNode newNode = new RemoteFilterNode(((RemoteFilterNode) node).getCondition(), targetSchema);
                newNode.addChild(child);
                return newNode;
            }
        }

        return visitChildren(node.getChildren(), context);
    }

    @Override
    public PlanNode visit(JoinNode node, PlanNode context) {
        if (node instanceof RemoteJoinNode && ((RemoteJoinNode) node).getSchema().equals(targetSchema)) {
            PlanNode left = node.getLeft().accept(this, context);
            PlanNode right = node.getRight().accept(this, context);

            if (left != null && right != null) {
                return new RemoteJoinNode(left, right, node.getJoinType(), node.getCondition(), node.getAlgorithm(), targetSchema);
            }
        }

        return visitChildren(node.getChildren(), context);
    }

    @Override
    public PlanNode visit(ProjectNode node, PlanNode context) {
        if (node instanceof RemoteProjectNode && ((RemoteProjectNode) node).getSchema().equals(targetSchema)) {
            PlanNode child = visitChildren(node.getChildren(), context);

            if (child != null) {
                RemoteProjectNode newNode = new RemoteProjectNode(node.getSelectList(), targetSchema);
                newNode.addChild(child);

                return newNode;
            }
        }

        return visitChildren(node.getChildren(), context);
    }
}
