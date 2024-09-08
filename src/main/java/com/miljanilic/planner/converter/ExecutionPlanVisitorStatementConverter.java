package com.miljanilic.planner.converter;

import com.miljanilic.planner.ExecutionPlanVisitor;
import com.miljanilic.planner.node.*;
import com.miljanilic.sql.ast.clause.*;
import com.miljanilic.sql.ast.expression.Expression;
import com.miljanilic.sql.ast.expression.Function;
import com.miljanilic.sql.ast.expression.binary.AndOperator;
import com.miljanilic.sql.ast.node.*;
import com.miljanilic.sql.ast.statement.SelectStatement;

import java.util.*;

public class ExecutionPlanVisitorStatementConverter implements ExecutionPlanStatementConverter, ExecutionPlanVisitor<SelectStatement, SelectStatement> {

    @Override
    public SelectStatement convert(PlanNode rootNode) {
        return rootNode.accept(this, null);
    }

    @Override
    public SelectStatement visit(ScanNode node, SelectStatement statement) {
        if (statement == null) {
            statement = new SelectStatement();
        }
        if (statement.getFromClause() == null) {
            statement.setFromClause(new FromClause(node.getFrom()));
        }
        return statement;
    }

    @Override
    public SelectStatement visit(FilterNode node, SelectStatement statement) {
        SelectStatement result = visitChildren(node, statement);
        Expression filterCondition = node.getCondition();

        WhereClause whereClause = result.getWhereClause();
        if (whereClause == null) {
            result.setWhereClause(new WhereClause(filterCondition));
        } else {
            whereClause.setCondition(new AndOperator(whereClause.getCondition(), filterCondition));
        }

        return result;
    }

    @Override
    public SelectStatement visit(JoinNode node, SelectStatement statement) {
        SelectStatement leftStatement = node.getLeft().accept(this, statement);
        SelectStatement rightStatement = node.getRight().accept(this, new SelectStatement());

        Join join = new Join(node.getJoinType(), rightStatement.getFromClause().getFrom(), node.getCondition());

        JoinClause joinClause = leftStatement.getJoinClause();
        if (joinClause == null) {
            joinClause = new JoinClause(new ArrayList<>());
            leftStatement.setJoinClause(joinClause);
        }
        joinClause.addJoin(join);

        return leftStatement;
    }

    @Override
    public SelectStatement visit(ProjectNode node, SelectStatement statement) {
        SelectStatement result = visitChildren(node, statement);
        SelectClause selectClause = result.getSelectClause();
        if (selectClause == null) {
            selectClause = new SelectClause(new ArrayList<>());
            result.setSelectClause(selectClause);
        }
        selectClause.getSelectList().addAll(node.getSelectList());
        return result;
    }

    @Override
    public SelectStatement visit(AggregateNode node, SelectStatement statement) {
        SelectStatement result = visitChildren(node, statement);

        if (!node.getGroupByExpressions().isEmpty()) {
            GroupByClause groupByClause = new GroupByClause(new GroupBy(node.getGroupByExpressions().getFirst()));
            result.setGroupByClause(groupByClause);
        }

        SelectClause selectClause = result.getSelectClause();
        if (selectClause == null) {
            selectClause = new SelectClause(new ArrayList<>());
            result.setSelectClause(selectClause);
        }
        for (Function function : node.getAggregateFunctions()) {
            selectClause.addSelect(new Select(function, null));
        }

        return result;
    }

    @Override
    public SelectStatement visit(SortNode node, SelectStatement statement) {
        SelectStatement result = visitChildren(node, statement);

        if (!node.getOrderByList().isEmpty()) {
            result.setOrderByClause(new OrderByClause(node.getOrderByList()));
        }

        return result;
    }

    @Override
    public SelectStatement visit(LimitNode node, SelectStatement statement) {
        SelectStatement result = visitChildren(node, statement);
        result.setLimitClause(new LimitClause(node.getLimit(), node.getOffset()));
        return result;
    }

    @Override
    public SelectStatement visit(HavingNode node, SelectStatement statement) {
        SelectStatement result = visitChildren(node, statement);
        result.setHavingClause(new HavingClause(node.getCondition()));
        return result;
    }

    private SelectStatement visitChildren(PlanNode node, SelectStatement statement) {
        SelectStatement result = statement;
        for (PlanNode child : node.getChildren()) {
            result = child.accept(this, result);
        }
        return result;
    }
}
