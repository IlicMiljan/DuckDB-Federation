package com.miljanilic.planner.rule;

import com.google.inject.Inject;
import com.miljanilic.catalog.data.Schema;
import com.miljanilic.planner.ExecutionPlannerException;
import com.miljanilic.planner.node.*;
import com.miljanilic.sql.ast.expression.Column;
import com.miljanilic.sql.ast.expression.Expression;
import com.miljanilic.sql.ast.expression.Function;
import com.miljanilic.sql.ast.node.OrderBy;
import com.miljanilic.sql.ast.node.Select;
import com.miljanilic.sql.ast.visitor.ASTColumnExtractingVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectDecompositionExecutionPlannerRule extends ExecutionPlannerRuleAdapter {
    private final ASTColumnExtractingVisitor columnListASTVisitor;

    @Inject
    public ProjectDecompositionExecutionPlannerRule(ASTColumnExtractingVisitor columnListASTVisitor) {
        this.columnListASTVisitor = columnListASTVisitor;
    }

    @Override
    public PlanNode visit(FilterNode node, PlanNode context) {
        List<PlanNode> children = visitChildren(node.getChildren(), context);

        List<Column> columnList = node.getCondition().accept(this.columnListASTVisitor, null);
        node.setChildren(children);

        return createRemoteProjectNodes(node, columnList);
    }

    @Override
    public PlanNode visit(JoinNode node, PlanNode context) {
        List<PlanNode> children = visitChildren(node.getChildren(), context);

        List<Column> columnList = node.getCondition().accept(this.columnListASTVisitor, null);
        node.setChildren(children);

        return createRemoteProjectNodes(node, columnList);
    }

    @Override
    public PlanNode visit(ProjectNode node, PlanNode context) {
        List<PlanNode> children = visitChildren(node.getChildren(), context);
        List<Column> columnList = new ArrayList<>();

        for (Select select : node.getSelectList()) {
            columnList.addAll(select.getExpression().accept(this.columnListASTVisitor, null));
        }

        node.setChildren(children);

        return createRemoteProjectNodes(node, columnList);
    }

    @Override
    public PlanNode visit(AggregateNode node, PlanNode context) {
        List<PlanNode> children = visitChildren(node.getChildren(), context);
        List<Column> columnList = new ArrayList<>();

        for (Expression expr : node.getGroupByExpressions()) {
            columnList.addAll(expr.accept(this.columnListASTVisitor, null));
        }

        for (Function func : node.getAggregateFunctions()) {
            columnList.addAll(func.accept(this.columnListASTVisitor, null));
        }

        node.setChildren(children);

        return createRemoteProjectNodes(node, columnList);
    }

    @Override
    public PlanNode visit(SortNode node, PlanNode context) {
        List<PlanNode> children = visitChildren(node.getChildren(), context);
        List<Column> columnList = new ArrayList<>();

        for (OrderBy orderBy : node.getOrderByList()) {
            columnList.addAll(orderBy.getExpression().accept(this.columnListASTVisitor, null));
        }

        node.setChildren(children);

        return createRemoteProjectNodes(node, columnList);
    }

    @Override
    public PlanNode visit(HavingNode node, PlanNode context) {
        List<PlanNode> children = visitChildren(node.getChildren(), context);

        List<Column> columnList = node.getCondition().accept(this.columnListASTVisitor, null);
        node.setChildren(children);

        return createRemoteProjectNodes(node, columnList);
    }

    private PlanNode createRemoteProjectNodes(PlanNode originalNode, List<Column> columnList) {
        Map<Schema, List<Select>> selectBySchema = new HashMap<>();

        for (Column column : columnList) {
            Schema schema = column.getFrom().getSchema();

            if (schema == null) {
                throw new ExecutionPlannerException("Schema not found.");
            }

            selectBySchema.computeIfAbsent(schema, k -> new ArrayList<>()).add(new Select(column, null));
        }

        PlanNode currentNode = originalNode;

        for (Map.Entry<Schema, List<Select>> entry : selectBySchema.entrySet()) {
            Schema schema = entry.getKey();
            List<Select> selectList = entry.getValue();

            if (!selectList.isEmpty()) {
                RemoteProjectNode remoteProjectNode = new RemoteProjectNode(selectList, schema);
                remoteProjectNode.addChild(currentNode);
                currentNode = remoteProjectNode;
            }
        }

        return currentNode;
    }
}
