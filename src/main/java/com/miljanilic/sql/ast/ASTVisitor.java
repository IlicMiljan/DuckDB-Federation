package com.miljanilic.sql.ast;

import com.miljanilic.sql.ast.clause.*;
import com.miljanilic.sql.ast.expression.*;
import com.miljanilic.sql.ast.expression.binary.*;
import com.miljanilic.sql.ast.node.*;
import com.miljanilic.sql.ast.statement.SelectStatement;

public interface ASTVisitor<T> {
    // Statement
    T visit(SelectStatement statement);

    // Clauses (in typical SQL order)
    T visit(SelectClause clause);
    T visit(FromClause clause);
    T visit(JoinClause clause);
    T visit(WhereClause clause);
    T visit(GroupByClause clause);
    T visit(HavingClause clause);
    T visit(OrderByClause clause);
    T visit(LimitClause clause);

    // Table-related
    T visit(Table table);
    T visit(SimpleJoin expression);

    // Column and Function
    T visit(Column expression);
    T visit(Function expression);

    // Literals
    T visit(LongValue expression);
    T visit(StringValue expression);

    // Comparison Operators
    T visit(EqualsTo expression);
    T visit(NotEqualsTo expression);
    T visit(GreaterThan expression);
    T visit(GreaterThanEquals expression);
    T visit(LessThan expression);
    T visit(LessThanEquals expression);

    // Logical Operators
    T visit(AndOperator expression);
    T visit(OrOperator expression);

    // Arithmetic Operators
    T visit(Addition expression);
    T visit(Subtraction expression);
    T visit(Multiplication expression);
    T visit(Division expression);
    T visit(Modulo expression);

    // Other Expressions
    T visit(ExpressionList expression);

    // Simple Clause Implementations
    T visit(SimpleSelect table);
    T visit(SimpleOrderBy table);
    T visit(SimpleGroupBy expression);
}
