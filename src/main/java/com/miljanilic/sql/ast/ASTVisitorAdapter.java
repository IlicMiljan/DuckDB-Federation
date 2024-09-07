package com.miljanilic.sql.ast;

import com.miljanilic.sql.ast.clause.*;
import com.miljanilic.sql.ast.expression.*;
import com.miljanilic.sql.ast.expression.binary.*;
import com.miljanilic.sql.ast.node.*;
import com.miljanilic.sql.ast.statement.SelectStatement;

public class ASTVisitorAdapter<T, S> implements ASTVisitor<T, S> {
    
    public T visit(ASTNode node, S context) {
        throw new UnsupportedOperationException("Unsupported node type: " + node.getClass().getSimpleName());
    }

    @Override
    public T visit(SelectStatement statement, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(SelectClause clause, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(FromClause clause, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(JoinClause clause, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(WhereClause clause, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(GroupByClause clause, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(HavingClause clause, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(OrderByClause clause, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(LimitClause clause, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(Table table, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(Join expression, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(Column expression, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(Function expression, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(LongValue expression, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(StringValue expression, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(EqualsTo expression, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(NotEqualsTo expression, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(GreaterThan expression, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(GreaterThanEquals expression, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(LessThan expression, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(LessThanEquals expression, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(AndOperator expression, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(OrOperator expression, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(Addition expression, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(Subtraction expression, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(Multiplication expression, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(Division expression, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(Modulo expression, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(ExpressionList expression, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(Select table, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(OrderBy table, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T visit(GroupBy expression, S context) {
        throw new UnsupportedOperationException("Operation not supported");
    }
}
