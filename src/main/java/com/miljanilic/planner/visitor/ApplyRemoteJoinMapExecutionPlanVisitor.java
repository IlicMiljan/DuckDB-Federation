package com.miljanilic.planner.visitor;

import com.miljanilic.planner.ExecutionPlanVisitor;
import com.miljanilic.planner.node.*;
import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.expression.Expression;
import com.miljanilic.sql.ast.expression.Function;
import com.miljanilic.sql.ast.node.OrderBy;
import com.miljanilic.sql.ast.node.Select;

import java.util.List;

public class ApplyRemoteJoinMapExecutionPlanVisitor implements ExecutionPlanVisitor<Void, Void> {
    private final ASTVisitor<Void, Void> astVisitor;

    public ApplyRemoteJoinMapExecutionPlanVisitor(
            ASTVisitor<Void, Void> astVisitor
    ) {
        this.astVisitor = astVisitor;
    }

    @Override
    public Void visit(ScanNode node, Void context) {
        node.getFrom().accept(astVisitor, null);

        visitChildren(node.getChildren(), context);
        return null;
    }

    @Override
    public Void visit(FilterNode node, Void context) {
        node.getCondition().accept(astVisitor, null);

        visitChildren(node.getChildren(), context);
        return null;
    }

    @Override
    public Void visit(JoinNode node, Void context) {
        node.getCondition().accept(astVisitor, null);

        visitChildren(node.getChildren(), context);
        return null;
    }

    @Override
    public Void visit(ProjectNode node, Void context) {
        for (Select select : node.getSelectList()) {
            select.accept(astVisitor, context);
        }

        visitChildren(node.getChildren(), context);
        return null;
    }

    @Override
    public Void visit(AggregateNode node, Void context) {
        for (Function function : node.getAggregateFunctions()) {
            function.accept(astVisitor, context);
        }

        for (Expression expression : node.getGroupByExpressions()) {
            expression.accept(astVisitor, context);
        }

        visitChildren(node.getChildren(), context);
        return null;
    }

    @Override
    public Void visit(SortNode node, Void context) {
        for (OrderBy orderBy : node.getOrderByList()) {
            orderBy.accept(astVisitor, null);
        }

        visitChildren(node.getChildren(), context);
        return null;
    }

    @Override
    public Void visit(LimitNode node, Void context) {
        visitChildren(node.getChildren(), context);
        return null;
    }

    @Override
    public Void visit(HavingNode node, Void context) {
        node.getCondition().accept(astVisitor, context);

        visitChildren(node.getChildren(), context);
        return null;
    }

    private void visitChildren(List<PlanNode> children, Void context) {
        for (PlanNode child : children) {
            child.accept(this, context);
        }
    }
}
