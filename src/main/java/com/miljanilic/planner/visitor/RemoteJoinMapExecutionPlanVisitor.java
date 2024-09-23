package com.miljanilic.planner.visitor;

import com.miljanilic.catalog.data.Table;
import com.miljanilic.planner.ExecutionPlanVisitor;
import com.miljanilic.planner.node.*;

import java.util.*;

public class RemoteJoinMapExecutionPlanVisitor implements ExecutionPlanVisitor<Void, Map<Table, com.miljanilic.sql.ast.node.Table>> {

    @Override
    public Void visit(ScanNode node, Map<Table, com.miljanilic.sql.ast.node.Table> context) {
        visitChildren(node.getChildren(), context);
        return null;
    }

    @Override
    public Void visit(FilterNode node, Map<Table, com.miljanilic.sql.ast.node.Table> context) {
        visitChildren(node.getChildren(), context);
        return null;
    }

    @Override
    public Void visit(JoinNode node, Map<Table, com.miljanilic.sql.ast.node.Table> context) {
        if (node instanceof RemoteJoinNode remoteJoinNode) {
            if (remoteJoinNode.getLeft() instanceof RemoteScanNode leftScanNode && remoteJoinNode.getRight() instanceof RemoteScanNode rightScanNode) {
                SortedSet<String> tableNames = new TreeSet<>();
                tableNames.add(leftScanNode.getFrom().getSchemaTable().getName());
                tableNames.add(rightScanNode.getFrom().getSchemaTable().getName());

                com.miljanilic.sql.ast.node.Table virtualTable = new com.miljanilic.sql.ast.node.Table(leftScanNode.getSchema(), null, String.join("_", tableNames), null);

                context.put(leftScanNode.getFrom().getSchemaTable(), virtualTable);
                context.put(rightScanNode.getFrom().getSchemaTable(), virtualTable);
            }
        }

        visitChildren(node.getChildren(), context);
        return null;
    }

    @Override
    public Void visit(ProjectNode node, Map<Table, com.miljanilic.sql.ast.node.Table> context) {
        visitChildren(node.getChildren(), context);
        return null;
    }

    @Override
    public Void visit(AggregateNode node, Map<Table, com.miljanilic.sql.ast.node.Table> context) {
        visitChildren(node.getChildren(), context);
        return null;
    }

    @Override
    public Void visit(SortNode node, Map<Table, com.miljanilic.sql.ast.node.Table> context) {
        visitChildren(node.getChildren(), context);
        return null;
    }

    @Override
    public Void visit(LimitNode node, Map<Table, com.miljanilic.sql.ast.node.Table> context) {
        visitChildren(node.getChildren(), context);
        return null;
    }

    @Override
    public Void visit(HavingNode node, Map<Table, com.miljanilic.sql.ast.node.Table> context) {
        visitChildren(node.getChildren(), context);
        return null;
    }

    private void visitChildren(List<PlanNode> children, Map<Table, com.miljanilic.sql.ast.node.Table> context) {
        for (PlanNode child : children) {
            child.accept(this, context);
        }
    }
}
