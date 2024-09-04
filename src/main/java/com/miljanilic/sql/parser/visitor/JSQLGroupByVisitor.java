package com.miljanilic.sql.parser.visitor;

import com.miljanilic.sql.ast.expression.Expression;
import com.miljanilic.sql.ast.node.GroupBy;
import com.miljanilic.sql.ast.node.SimpleGroupBy;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.statement.select.GroupByElement;
import net.sf.jsqlparser.statement.select.GroupByVisitor;

public class JSQLGroupByVisitor implements GroupByVisitor<GroupBy> {
    private final ExpressionVisitor<Expression> expressionVisitor;

    public JSQLGroupByVisitor(ExpressionVisitor<Expression> expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public <S> GroupBy visit(GroupByElement groupByElement, S context) {
        if (!groupByElement.getGroupingSets().isEmpty()) {
            throw new UnsupportedOperationException("GROUPING SETS are not supported.");
        }

        ExpressionList<?> expressionList = (ExpressionList<?>) groupByElement.getGroupByExpressionList();
        return new SimpleGroupBy(expressionList.accept(expressionVisitor, context));
    }
}
