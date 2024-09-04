package com.miljanilic.sql.parser.visitor;

import com.miljanilic.sql.ast.expression.*;
import com.miljanilic.sql.ast.expression.binary.*;
import com.miljanilic.sql.ast.node.From;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.FromItemVisitor;

import java.util.ArrayList;
import java.util.List;

public class JSQLExpressionVisitor extends ExpressionVisitorAdapter<Expression> {
    private final FromItemVisitor<From> fromItemVisitor;

    public JSQLExpressionVisitor(FromItemVisitor<From> fromItemVisitor) {
        this.fromItemVisitor = fromItemVisitor;
    }

    @Override
    public <S> Expression visit(net.sf.jsqlparser.schema.Column column, S context) {
        return new Column(
                column.getColumnName(),
                column.getTable() != null ? column.getTable().accept(this.fromItemVisitor, context) : null
        );
    }

    @Override
    public <S> Expression visit(AllColumns allColumns, S context) {
        return new Column(allColumns.toString(), null);
    }

    @Override
    public <S> Expression visit(AllTableColumns allTableColumns, S context) {
        return new Column(allTableColumns.toString(), allTableColumns.getTable().accept(this.fromItemVisitor, context));
    }

    @Override
    public <S> Expression visit(net.sf.jsqlparser.expression.LongValue longValue, S context) {
        return new LongValue(longValue.getValue());
    }

    @Override
    public <S> Expression visit(net.sf.jsqlparser.expression.StringValue stringValue, S context) {
        return new StringValue(stringValue.getValue());
    }

    @Override
    public <S> Expression visit(net.sf.jsqlparser.expression.operators.relational.EqualsTo equalsTo, S context) {
        return new EqualsTo(
                equalsTo.getLeftExpression().accept(this, context),
                equalsTo.getRightExpression().accept(this, context)
        );
    }

    public <S> Expression visit(net.sf.jsqlparser.expression.operators.relational.NotEqualsTo notEqualsTo, S context) {
        return new NotEqualsTo(
                notEqualsTo.getLeftExpression().accept(this, context),
                notEqualsTo.getRightExpression().accept(this, context)
        );
    }

    @Override
    public <S> Expression visit(net.sf.jsqlparser.expression.operators.relational.GreaterThan greaterThan, S context) {
        return new GreaterThan(
                greaterThan.getLeftExpression().accept(this, context),
                greaterThan.getRightExpression().accept(this, context)
        );
    }

    @Override
    public <S> Expression visit(net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals greaterThanEquals, S context) {
        return new GreaterThanEquals(
                greaterThanEquals.getLeftExpression().accept(this, context),
                greaterThanEquals.getRightExpression().accept(this, context)
        );
    }

    @Override
    public <S> Expression visit(MinorThan minorThan, S context) {
        return new LessThan(
                minorThan.getLeftExpression().accept(this, context),
                minorThan.getRightExpression().accept(this, context)
        );
    }

    @Override
    public <S> Expression visit(MinorThanEquals minorThanEquals, S context) {
        return new LessThanEquals(
                minorThanEquals.getLeftExpression().accept(this, context),
                minorThanEquals.getRightExpression().accept(this, context)
        );
    }

    @Override
    public <S> Expression visit(net.sf.jsqlparser.expression.operators.arithmetic.Addition addition, S context) {
        return new Addition(
                addition.getLeftExpression().accept(this, context),
                addition.getRightExpression().accept(this, context)
        );
    }

    @Override
    public <S> Expression visit(net.sf.jsqlparser.expression.operators.arithmetic.Division division, S context) {
        return new Division(
                division.getLeftExpression().accept(this, context),
                division.getRightExpression().accept(this, context)
        );
    }

    @Override
    public <S> Expression visit(net.sf.jsqlparser.expression.operators.arithmetic.Modulo modulo, S context) {
        return new Modulo(
                modulo.getLeftExpression().accept(this, context),
                modulo.getRightExpression().accept(this, context)
        );
    }

    @Override
    public <S> Expression visit(net.sf.jsqlparser.expression.operators.arithmetic.Multiplication multiplication, S context) {
        return new Multiplication(
                multiplication.getLeftExpression().accept(this, context),
                multiplication.getRightExpression().accept(this, context)
        );
    }

    @Override
    public <S> Expression visit(net.sf.jsqlparser.expression.operators.arithmetic.Subtraction subtraction, S context) {
        return new Subtraction(
                subtraction.getLeftExpression().accept(this, context),
                subtraction.getRightExpression().accept(this, context)
        );
    }

    @Override
    public <S> Expression visit(AndExpression andExpression, S context) {
        return new AndOperator(
                andExpression.getLeftExpression().accept(this, context),
                andExpression.getRightExpression().accept(this, context)
        );
    }

    @Override
    public <S> Expression visit(OrExpression orExpression, S context) {
        return new OrOperator(
                orExpression.getLeftExpression().accept(this, context),
                orExpression.getRightExpression().accept(this, context)
        );
    }

    @Override
    public <S> Expression visit(net.sf.jsqlparser.expression.Function function, S context) {
        return new Function(
                function.getName(),
                function.getParameters().accept(this, context)
        );
    }

    @Override
    public <S> Expression visit(net.sf.jsqlparser.expression.operators.relational.ExpressionList<? extends net.sf.jsqlparser.expression.Expression> expressionList, S context) {
        List<Expression> expressions = new ArrayList<>();

        for (net.sf.jsqlparser.expression.Expression expr : expressionList) {
            expressions.add(expr.accept(this, context));
        }

        return new ExpressionList(expressions);
    }
}
