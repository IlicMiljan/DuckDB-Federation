package com.miljanilic.sql.ast;

import com.miljanilic.sql.ast.clause.*;
import com.miljanilic.sql.ast.expression.*;
import com.miljanilic.sql.ast.expression.binary.*;
import com.miljanilic.sql.ast.node.*;
import com.miljanilic.sql.ast.statement.SelectStatement;

public interface ASTVisitor<T, S> {
    // Statement
    T visit(SelectStatement statement, S context);

    // Clauses (in typical SQL order)
    T visit(SelectClause clause, S context);
    T visit(FromClause clause, S context);
    T visit(JoinClause clause, S context);
    T visit(WhereClause clause, S context);
    T visit(GroupByClause clause, S context);
    T visit(HavingClause clause, S context);
    T visit(OrderByClause clause, S context);
    T visit(LimitClause clause, S context);

    // Table-related
    T visit(Table table, S context);
    T visit(Join expression, S context);

    // Column and Function
    T visit(Column expression, S context);
    T visit(Function expression, S context);

    // Literals
    T visit(LongValue expression, S context);
    T visit(StringValue expression, S context);

    // Comparison Operators
    T visit(EqualsTo expression, S context);
    T visit(NotEqualsTo expression, S context);
    T visit(GreaterThan expression, S context);
    T visit(GreaterThanEquals expression, S context);
    T visit(LessThan expression, S context);
    T visit(LessThanEquals expression, S context);

    // Logical Operators
    T visit(AndOperator expression, S context);
    T visit(OrOperator expression, S context);

    // Arithmetic Operators
    T visit(Addition expression, S context);
    T visit(Subtraction expression, S context);
    T visit(Multiplication expression, S context);
    T visit(Division expression, S context);
    T visit(Modulo expression, S context);

    // Other Expressions
    T visit(ExpressionList expression, S context);

    // Simple Clause Implementations
    T visit(Select table, S context);
    T visit(OrderBy table, S context);
    T visit(GroupBy expression, S context);
}
