package com.miljanilic.sql.ast.visitor;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.clause.*;
import com.miljanilic.sql.ast.expression.*;
import com.miljanilic.sql.ast.expression.binary.*;
import com.miljanilic.sql.ast.node.*;
import com.miljanilic.sql.ast.statement.SelectStatement;

import java.util.ArrayList;
import java.util.List;

public class ASTColumnExtractingVisitor implements ASTVisitor<List<Column>, Void> {
    @Override
    public List<Column> visit(SelectStatement statement, Void context) {
        List<Column> columns = new ArrayList<>();

        columns.addAll(statement.getSelectClause().accept(this, context));
        columns.addAll(statement.getFromClause().accept(this, context));

        if (statement.getJoinClause() != null) {
            columns.addAll(statement.getJoinClause().accept(this, context));
        }

        if (statement.getWhereClause() != null) {
            columns.addAll(statement.getWhereClause().accept(this, context));
        }

        if (statement.getGroupByClause() != null) {
            columns.addAll(statement.getGroupByClause().accept(this, context));
        }

        if (statement.getHavingClause() != null) {
            columns.addAll(statement.getHavingClause().accept(this, context));
        }

        if (statement.getOrderByClause() != null) {
            columns.addAll(statement.getOrderByClause().accept(this, context));
        }

        if (statement.getLimitClause() != null) {
            columns.addAll(statement.getLimitClause().accept(this, context));
        }

        return columns;
    }

    @Override
    public List<Column> visit(SelectClause clause, Void context) {
        List<Column> columns = new ArrayList<>();
        for (Select select : clause.getSelectList()) {
            columns.addAll(select.accept(this, context));
        }
        return columns;
    }

    @Override
    public List<Column> visit(FromClause clause, Void context) {
        return clause.getFrom().accept(this, context);
    }

    @Override
    public List<Column> visit(JoinClause clause, Void context) {
        List<Column> columns = new ArrayList<>();

        for (Join join : clause.getJoinList()) {
            columns.addAll(join.accept(this, context));
        }

        return columns;
    }

    @Override
    public List<Column> visit(WhereClause clause, Void context) {
        return clause.getCondition().accept(this, context);
    }

    @Override
    public List<Column> visit(GroupByClause clause, Void context) {
        return clause.getGroupBy().accept(this, context);
    }

    @Override
    public List<Column> visit(HavingClause clause, Void context) {
        return clause.getCondition().accept(this, context);
    }

    @Override
    public List<Column> visit(OrderByClause clause, Void context) {
        List<Column> columns = new ArrayList<>();

        for (OrderBy orderBy : clause.getOrderByList()) {
            columns.addAll(orderBy.accept(this, context));
        }

        return columns;
    }

    @Override
    public List<Column> visit(LimitClause clause, Void context) {
        List<Column> columns = new ArrayList<>(clause.getLimit().accept(this, context));

        if (clause.getOffset() != null) {
            columns.addAll(clause.getOffset().accept(this, context));
        }

        return columns;
    }

    @Override
    public List<Column> visit(Table table, Void context) {
        return new ArrayList<>();
    }

    @Override
    public List<Column> visit(Join expression, Void context) {
        List<Column> columns = new ArrayList<>();

        columns.addAll(expression.getFrom().accept(this, context));
        columns.addAll(expression.getConditions().accept(this, context));

        return columns;
    }

    @Override
    public List<Column> visit(Column expression, Void context) {
        List<Column> columns = new ArrayList<>();
        columns.add(expression);

        return columns;
    }

    @Override
    public List<Column> visit(Function expression, Void context) {
        return expression.getArguments().accept(this, context);
    }

    @Override
    public List<Column> visit(LongValue expression, Void context) {
        return new ArrayList<>();
    }

    @Override
    public List<Column> visit(StringValue expression, Void context) {
        return new ArrayList<>();
    }

    @Override
    public List<Column> visit(EqualsTo expression, Void context) {
        return visitBinaryExpression(expression);
    }

    @Override
    public List<Column> visit(NotEqualsTo expression, Void context) {
        return visitBinaryExpression(expression);
    }

    @Override
    public List<Column> visit(GreaterThan expression, Void context) {
        return visitBinaryExpression(expression);
    }

    @Override
    public List<Column> visit(GreaterThanEquals expression, Void context) {
        return visitBinaryExpression(expression);
    }

    @Override
    public List<Column> visit(LessThan expression, Void context) {
        return visitBinaryExpression(expression);
    }

    @Override
    public List<Column> visit(LessThanEquals expression, Void context) {
        return visitBinaryExpression(expression);
    }

    @Override
    public List<Column> visit(AndOperator expression, Void context) {
        return visitBinaryExpression(expression);
    }

    @Override
    public List<Column> visit(OrOperator expression, Void context) {
        return visitBinaryExpression(expression);
    }

    @Override
    public List<Column> visit(Addition expression, Void context) {
        return visitBinaryExpression(expression);
    }

    @Override
    public List<Column> visit(Subtraction expression, Void context) {
        return visitBinaryExpression(expression);
    }

    @Override
    public List<Column> visit(Multiplication expression, Void context) {
        return visitBinaryExpression(expression);
    }

    @Override
    public List<Column> visit(Division expression, Void context) {
        return visitBinaryExpression(expression);
    }

    @Override
    public List<Column> visit(Modulo expression, Void context) {
        return visitBinaryExpression(expression);
    }

    @Override
    public List<Column> visit(ExpressionList expression, Void context) {
        List<Column> columns = new ArrayList<>();

        for (Expression expr : expression.getExpressions()) {
            columns.addAll(expr.accept(this, context));
        }

        return columns;
    }

    @Override
    public List<Column> visit(Select select, Void context) {
        return select.getExpression().accept(this, context);
    }

    @Override
    public List<Column> visit(OrderBy orderBy, Void context) {
        return orderBy.getExpression().accept(this, context);
    }

    @Override
    public List<Column> visit(GroupBy groupBy, Void context) {
        return groupBy.getExpression().accept(this, context);
    }

    private List<Column> visitBinaryExpression(Binary expression) {
        List<Column> columns = new ArrayList<>();

        columns.addAll(expression.getLeft().accept(this, null));
        columns.addAll(expression.getRight().accept(this, null));

        return columns;
    }
}
