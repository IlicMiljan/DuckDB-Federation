package com.miljanilic.sql.deparser.visitor;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.ast.expression.ExpressionList;
import com.miljanilic.sql.ast.expression.Function;
import com.miljanilic.sql.ast.expression.LongValue;
import com.miljanilic.sql.ast.expression.StringValue;
import com.miljanilic.sql.ast.expression.binary.*;
import com.miljanilic.sql.ast.visitor.ASTVisitorAdapter;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItem;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class AstToJSqlExpressionVisitor extends ASTVisitorAdapter<Expression, Void> {
    private final AstToJSqlFromItemVisitor fromItemVisitor;

    @Inject
    public AstToJSqlExpressionVisitor(AstToJSqlFromItemVisitor fromItemVisitor) {
        this.fromItemVisitor = fromItemVisitor;
    }

    @Override
    public Expression visit(com.miljanilic.sql.ast.expression.Column column, Void context) {
        FromItem jsqlFromItem = column.getFrom().accept(fromItemVisitor, null);
        Table jsqlTable = null;

        if (jsqlFromItem instanceof Table) {
            jsqlTable = (Table) jsqlFromItem;
        }

        return new Column(
                jsqlTable,
                column.getName()
        );
    }

    @Override
    public Expression visit(Function function, Void context) {
        net.sf.jsqlparser.expression.Function jsqlFunction = new net.sf.jsqlparser.expression.Function();

        jsqlFunction.setName(function.getFunctionName());
        jsqlFunction.setParameters(function.getArguments().accept(this, context));
        jsqlFunction.setDistinct(function.isDistinct());

        return jsqlFunction;
    }

    @Override
    public Expression visit(LongValue longValue, Void context) {
        return new net.sf.jsqlparser.expression.LongValue(longValue.getValue());
    }

    @Override
    public Expression visit(StringValue stringValue, Void context) {
        return new net.sf.jsqlparser.expression.StringValue(stringValue.getValue());
    }

    @Override
    public Expression visit(EqualsTo equalsTo, Void context) {
        return new net.sf.jsqlparser.expression.operators.relational.EqualsTo(
                equalsTo.getLeft().accept(this, context),
                equalsTo.getRight().accept(this, context)
        );
    }

    @Override
    public Expression visit(NotEqualsTo notEqualsTo, Void context) {
        return new net.sf.jsqlparser.expression.operators.relational.NotEqualsTo(
                notEqualsTo.getLeft().accept(this, context),
                notEqualsTo.getRight().accept(this, context)
        );
    }

    @Override
    public Expression visit(GreaterThan greaterThan, Void context) {
        return new net.sf.jsqlparser.expression.operators.relational.GreaterThan(
                greaterThan.getLeft().accept(this, context),
                greaterThan.getRight().accept(this, context)
        );
    }

    @Override
    public Expression visit(GreaterThanEquals greaterThanEquals, Void context) {
        return new net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals(
                greaterThanEquals.getLeft().accept(this, context),
                greaterThanEquals.getRight().accept(this, context)
        );
    }

    @Override
    public Expression visit(LessThan lessThan, Void context) {
        return new MinorThan(
                lessThan.getLeft().accept(this, context),
                lessThan.getRight().accept(this, context)
        );
    }

    @Override
    public Expression visit(LessThanEquals lessThanEquals, Void context) {
        return new MinorThanEquals(
                lessThanEquals.getLeft().accept(this, context),
                lessThanEquals.getRight().accept(this, context)
        );
    }

    @Override
    public Expression visit(AndOperator andOperator, Void context) {
        return new AndExpression(
                andOperator.getLeft().accept(this, context),
                andOperator.getRight().accept(this, context)
        );
    }

    @Override
    public Expression visit(OrOperator orOperator, Void context) {
        return new OrExpression(
                orOperator.getLeft().accept(this, context),
                orOperator.getRight().accept(this, context)
        );
    }

    @Override
    public Expression visit(Addition addition, Void context) {
        return new net.sf.jsqlparser.expression.operators.arithmetic.Addition(
                addition.getLeft().accept(this, context),
                addition.getRight().accept(this, context)
        );
    }

    @Override
    public Expression visit(Subtraction subtraction, Void context) {
        return new net.sf.jsqlparser.expression.operators.arithmetic.Subtraction(
                subtraction.getLeft().accept(this, context),
                subtraction.getRight().accept(this, context)
        );
    }

    @Override
    public Expression visit(Multiplication multiplication, Void context) {
        return new net.sf.jsqlparser.expression.operators.arithmetic.Multiplication(
                multiplication.getLeft().accept(this, context),
                multiplication.getRight().accept(this, context)
        );
    }

    @Override
    public Expression visit(Division division, Void context) {
        return new net.sf.jsqlparser.expression.operators.arithmetic.Division(
                division.getLeft().accept(this, context),
                division.getRight().accept(this, context)
        );
    }

    @Override
    public Expression visit(Modulo modulo, Void context) {
        return new net.sf.jsqlparser.expression.operators.arithmetic.Modulo(
                modulo.getLeft().accept(this, context),
                modulo.getRight().accept(this, context)
        );
    }

    @Override
    public Expression visit(ExpressionList expressionList, Void context) {
        List<Expression> expressions = new ArrayList<>();

        for (com.miljanilic.sql.ast.expression.Expression expression : expressionList.getExpressions()) {
            expressions.add(expression.accept(this, context));
        }

        return new net.sf.jsqlparser.expression.operators.relational.ExpressionList<>(expressions);
    }
}
