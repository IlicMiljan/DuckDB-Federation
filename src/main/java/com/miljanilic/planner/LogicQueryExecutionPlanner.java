package com.miljanilic.planner;

import com.google.inject.Singleton;
import com.miljanilic.planner.node.*;
import com.miljanilic.sql.ast.visitor.ASTVisitorAdapter;
import com.miljanilic.sql.ast.clause.*;
import com.miljanilic.sql.ast.expression.Expression;
import com.miljanilic.sql.ast.expression.Function;
import com.miljanilic.sql.ast.node.Join;
import com.miljanilic.sql.ast.node.Select;
import com.miljanilic.sql.ast.node.Table;
import com.miljanilic.sql.ast.statement.SelectStatement;
import com.miljanilic.sql.ast.statement.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * LogicQueryExecutionPlanner is responsible for transforming an Abstract Syntax Tree (AST)
 * representation of a SQL query into an execution plan. This class implements the Visitor
 * pattern to traverse the AST and build corresponding execution nodes.
 * <p>
 * The planner creates a tree of PlanNode objects that represent the logical steps
 * needed to execute the query. These steps include operations such as scanning tables,
 * applying filters, performing joins, grouping and aggregating data, sorting, and limiting results.
 * <p>
 * This class extends ASTVisitorAdapter<PlanNode, PlanNode>, where the first
 * PlanNode type parameter represents the return type of visit methods, and the second
 * represents the type of the context passed between visit methods (in this case, the child node).
 */
@Singleton
public class LogicQueryExecutionPlanner extends ASTVisitorAdapter<PlanNode, PlanNode> implements ExecutionPlanner {

    public PlanNode plan(Statement statement) {
        if (!(statement instanceof SelectStatement)) {
            throw new ExecutionPlannerException("Only SELECT statements are supported.");
        }

        return statement.accept(this, null);
    }

    /**
     * Visits a SelectStatement node and creates the complete execution plan.
     * This method orchestrates the creation of the entire execution plan by visiting
     * each clause of the SELECT statement in the appropriate order.
     *
     * @param statement The SelectStatement AST node
     * @param child The child execution node (unused in this method)
     * @return The root of the execution plan tree
     */
    @Override
    public PlanNode visit(SelectStatement statement, PlanNode child) {
        PlanNode plan = statement.getFromClause().accept(this, null);

        if (statement.getJoinClause() != null) {
            plan = statement.getJoinClause().accept(this, plan);
        }

        if (statement.getWhereClause() != null) {
            plan = statement.getWhereClause().accept(this, plan);
        }

        if (statement.getGroupByClause() != null) {
            plan = statement.getGroupByClause().accept(this, plan);
        }

        if (statement.getHavingClause() != null) {
            plan = statement.getHavingClause().accept(this, plan);
        }

        plan = statement.getSelectClause().accept(this, plan);

        if (statement.getOrderByClause() != null) {
            plan = statement.getOrderByClause().accept(this, plan);
        }

        if (statement.getLimitClause() != null) {
            plan = statement.getLimitClause().accept(this, plan);
        }

        return plan;
    }

    /**
     * Visits a SelectClause node and creates ProjectNode and AggregateNode as needed.
     * This method separates regular columns from aggregate functions in the SELECT list.
     * It creates an AggregateNode for any aggregate functions found, and a ProjectNode
     * for the remaining columns.
     *
     * @param clause The SelectClause AST node
     * @param child The child execution node
     * @return A ProjectNode (potentially with an AggregateNode as its child)
     */
    @Override
    public PlanNode visit(SelectClause clause, PlanNode child) {
        List<Select> selectList = clause.getSelectList();

        List<Select> projectList = new ArrayList<>();
        List<Function> functionList = new ArrayList<>();

        for (Select select : selectList) {
            Expression expression = select.getExpression();
            if (expression instanceof Function) {
                functionList.add((Function) expression);
            } else {
                projectList.add(select);
            }
        }

        AggregateNode aggregateNode = new AggregateNode(new ArrayList<>(), functionList);
        aggregateNode.addChild(child);

        ProjectNode projectNode = new ProjectNode(projectList);
        projectNode.addChild(aggregateNode);
        return projectNode;
    }

    /**
     * Visits a FromClause node and delegates to the appropriate node visitor.
     *
     * @param clause The FromClause AST node
     * @param child The child execution node
     * @return The result of visiting the From node within the clause
     */
    @Override
    public PlanNode visit(FromClause clause, PlanNode child) {
        return clause.getFrom().accept(this, child);
    }

    /**
     * Visits a JoinClause node and creates a tree of JoinNodes.
     * This method handles multiple joins by creating a left-deep tree of JoinNodes.
     *
     * @param clause The JoinClause AST node
     * @param child The child execution node (represents the left side of the first join)
     * @return The root JoinNode of the join tree
     */
    @Override
    public PlanNode visit(JoinClause clause, PlanNode child) {
        List<Join> joins = clause.getJoinList();

        PlanNode left = child;

        for (Join join : joins) {
            PlanNode right = join.getFrom().accept(this, null);

            left = new JoinNode(
                    left,
                    right,
                    join.getJoinType(),
                    join.getConditions(), JoinNode.JoinAlgorithm.HASH_JOIN
            );
        }

        return left;
    }

    /**
     * Visits a WhereClause node and creates a FilterNode.
     *
     * @param clause The WhereClause AST node
     * @param child The child execution node
     * @return A FilterNode with the specified condition
     */
    @Override
    public PlanNode visit(WhereClause clause, PlanNode child) {
        FilterNode filterNode = new FilterNode(clause.getCondition());
        filterNode.addChild(child);

        return filterNode;
    }

    /**
     * Visits a GroupByClause node and creates an AggregateNode.
     * Note that this method creates an AggregateNode with empty aggregate functions list.
     * Aggregate functions are typically handled in the SelectClause visit method.
     *
     * @param clause The GroupByClause AST node
     * @param child The child execution node
     * @return An AggregateNode with the specified grouping expressions
     */
    @Override
    public PlanNode visit(GroupByClause clause, PlanNode child) {
        AggregateNode aggregateNode = new AggregateNode(
                List.of(clause.getGroupBy().getExpression()),
                new ArrayList<>()
        );
        aggregateNode.addChild(child);

        return aggregateNode;
    }

    /**
     * Visits a HavingClause node and creates a HavingNode.
     *
     * @param clause The HavingClause AST node
     * @param child The child execution node
     * @return A HavingNode with the specified condition
     */
    @Override
    public PlanNode visit(HavingClause clause, PlanNode child) {
        HavingNode havingNode = new HavingNode(clause.getCondition());
        havingNode.addChild(child);

        return havingNode;
    }

    /**
     * Visits an OrderByClause node and creates a SortNode.
     *
     * @param clause The OrderByClause AST node
     * @param child The child execution node
     * @return A SortNode with the specified ordering expressions
     */
    @Override
    public PlanNode visit(OrderByClause clause, PlanNode child) {
        SortNode sortNode = new SortNode(clause.getOrderByList());
        sortNode.addChild(child);

        return sortNode;
    }

    /**
     * Visits a LimitClause node and creates a LimitNode.
     *
     * @param clause The LimitClause AST node
     * @param child The child execution node
     * @return A LimitNode with the specified limit and offset
     */
    @Override
    public PlanNode visit(LimitClause clause, PlanNode child) {
        LimitNode limitNode = new LimitNode(clause.getLimit(), clause.getOffset());
        limitNode.addChild(child);

        return limitNode;
    }

    /**
     * Visits a Table node and creates a ScanNode.
     *
     * @param table The Table AST node
     * @param child The child execution node (unused in this method)
     * @return A ScanNode representing the table scan operation
     */
    @Override
    public PlanNode visit(Table table, PlanNode child) {
        return new ScanNode(table, null);
    }
}
