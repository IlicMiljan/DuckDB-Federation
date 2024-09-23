package com.miljanilic.sql.ast.visitor;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.clause.*;
import com.miljanilic.sql.ast.expression.*;
import com.miljanilic.sql.ast.expression.binary.*;
import com.miljanilic.sql.ast.node.*;
import com.miljanilic.sql.ast.statement.SelectStatement;

import java.util.Map;

public class ASTFromReplacingVisitor implements ASTVisitor<Void, Void> {
    private final Map<com.miljanilic.catalog.data.Table, Table> remoteJoinMap;

    public ASTFromReplacingVisitor(Map<com.miljanilic.catalog.data.Table, Table> remoteJoinMap) {
        this.remoteJoinMap = remoteJoinMap;
    }

    @Override
    public Void visit(SelectStatement statement, Void context) {
        statement.getSelectClause().accept(this, context);
        statement.getFromClause().accept(this, context);

        if (statement.getJoinClause() != null) {
           statement.getJoinClause().accept(this, context);
        }

        if (statement.getWhereClause() != null) {
            statement.getWhereClause().accept(this, context);
        }

        if (statement.getGroupByClause() != null) {
            statement.getGroupByClause().accept(this, context);
        }

        if (statement.getHavingClause() != null) {
            statement.getHavingClause().accept(this, context);
        }

        if (statement.getOrderByClause() != null) {
            statement.getOrderByClause().accept(this, context);
        }

        if (statement.getLimitClause() != null) {
            statement.getLimitClause().accept(this, context);
        }

        return null;
    }

    @Override
    public Void visit(SelectClause clause, Void context) {
        for (Select select : clause.getSelectList()) {
            select.accept(this, context);
        }

        return null;
    }

    @Override
    public Void visit(FromClause clause, Void context) {
        return clause.getFrom().accept(this, context);
    }

    @Override
    public Void visit(JoinClause clause, Void context) {
        for (Join join : clause.getJoinList()) {
            join.accept(this, context);
        }

        return null;
    }

    @Override
    public Void visit(WhereClause clause, Void context) {
        return clause.getCondition().accept(this, context);
    }

    @Override
    public Void visit(GroupByClause clause, Void context) {
        return clause.getGroupBy().accept(this, context);
    }

    @Override
    public Void visit(HavingClause clause, Void context) {
        return clause.getCondition().accept(this, context);
    }

    @Override
    public Void visit(OrderByClause clause, Void context) {
        for (OrderBy orderBy : clause.getOrderByList()) {
            orderBy.accept(this, context);
        }

        return null;
    }

    @Override
    public Void visit(LimitClause clause, Void context) {
        if (clause.getOffset() != null) {
            clause.getOffset().accept(this, context);
        }

        return null;
    }

    @Override
    public Void visit(Table table, Void context) {
        return null;
    }

    @Override
    public Void visit(Join expression, Void context) {
        expression.getFrom().accept(this, context);
        expression.getConditions().accept(this, context);

        return null;
    }

    @Override
    public Void visit(Column expression, Void context) {
        From from = expression.getFrom();

        if (this.remoteJoinMap.containsKey(from.getSchemaTable())) {
            expression.setFrom(this.remoteJoinMap.get(from.getSchemaTable()));
        }

        return null;
    }

    @Override
    public Void visit(Function expression, Void context) {
        return expression.getArguments().accept(this, context);
    }

    @Override
    public Void visit(LongValue expression, Void context) {
        return null;
    }

    @Override
    public Void visit(StringValue expression, Void context) {
        return null;
    }

    @Override
    public Void visit(EqualsTo expression, Void context) {
        return visitBinaryExpression(expression);
    }

    @Override
    public Void visit(NotEqualsTo expression, Void context) {
        return visitBinaryExpression(expression);
    }

    @Override
    public Void visit(GreaterThan expression, Void context) {
        return visitBinaryExpression(expression);
    }

    @Override
    public Void visit(GreaterThanEquals expression, Void context) {
        return visitBinaryExpression(expression);
    }

    @Override
    public Void visit(LessThan expression, Void context) {
        return visitBinaryExpression(expression);
    }

    @Override
    public Void visit(LessThanEquals expression, Void context) {
        return visitBinaryExpression(expression);
    }

    @Override
    public Void visit(AndOperator expression, Void context) {
        return visitBinaryExpression(expression);
    }

    @Override
    public Void visit(OrOperator expression, Void context) {
        return visitBinaryExpression(expression);
    }

    @Override
    public Void visit(Addition expression, Void context) {
        return visitBinaryExpression(expression);
    }

    @Override
    public Void visit(Subtraction expression, Void context) {
        return visitBinaryExpression(expression);
    }

    @Override
    public Void visit(Multiplication expression, Void context) {
        return visitBinaryExpression(expression);
    }

    @Override
    public Void visit(Division expression, Void context) {
        return visitBinaryExpression(expression);
    }

    @Override
    public Void visit(Modulo expression, Void context) {
        return visitBinaryExpression(expression);
    }

    @Override
    public Void visit(ExpressionList expression, Void context) {
        for (Expression expr : expression.getExpressions()) {
            expr.accept(this, context);
        }

        return null;
    }

    @Override
    public Void visit(Select select, Void context) {
        return select.getExpression().accept(this, context);
    }

    @Override
    public Void visit(OrderBy orderBy, Void context) {
        return orderBy.getExpression().accept(this, context);
    }

    @Override
    public Void visit(GroupBy groupBy, Void context) {
        return groupBy.getExpression().accept(this, context);
    }

    private Void visitBinaryExpression(Binary expression) {
        expression.getLeft().accept(this, null);
        expression.getRight().accept(this, null);

        return null;
    }
}
