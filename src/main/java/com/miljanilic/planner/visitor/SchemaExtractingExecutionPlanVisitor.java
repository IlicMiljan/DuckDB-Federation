package com.miljanilic.planner.visitor;

import com.miljanilic.catalog.data.Schema;
import com.miljanilic.planner.ExecutionPlanVisitor;
import com.miljanilic.planner.node.*;

import java.util.List;
import java.util.Set;

public class SchemaExtractingExecutionPlanVisitor implements ExecutionPlanVisitor<Void, Set<Schema>> {
    @Override
    public Void visit(ScanNode node, Set<Schema> schemas) {
        if (node instanceof RemoteScanNode) {
            schemas.add(((RemoteScanNode) node).getSchema());
        }

        return null;
    }

    @Override
    public Void visit(FilterNode node, Set<Schema> schemas) {
        if (node instanceof RemoteFilterNode) {
            schemas.add(((RemoteFilterNode) node).getSchema());
        }
        visitChildren(node.getChildren(), schemas);
        return null;
    }

    @Override
    public Void visit(JoinNode node, Set<Schema> schemas) {
        if (node instanceof RemoteJoinNode) {
            schemas.add(((RemoteJoinNode) node).getSchema());
        }
        visitChildren(node.getChildren(), schemas);
        return null;
    }

    @Override
    public Void visit(ProjectNode node, Set<Schema> schemas) {
        if (node instanceof RemoteProjectNode) {
            schemas.add(((RemoteProjectNode) node).getSchema());
        }
        visitChildren(node.getChildren(), schemas);
        return null;
    }

    @Override
    public Void visit(AggregateNode node, Set<Schema> schemas) {
        visitChildren(node.getChildren(), schemas);
        return null;
    }

    @Override
    public Void visit(SortNode node, Set<Schema> schemas) {
        visitChildren(node.getChildren(), schemas);
        return null;
    }

    @Override
    public Void visit(LimitNode node, Set<Schema> schemas) {
        visitChildren(node.getChildren(), schemas);
        return null;
    }

    @Override
    public Void visit(HavingNode node, Set<Schema> schemas) {
        visitChildren(node.getChildren(), schemas);
        return null;
    }

    private void visitChildren(List<PlanNode> children, Set<Schema> schemas) {
        for (PlanNode child : children) {
            child.accept(this, schemas);
        }
    }
}
