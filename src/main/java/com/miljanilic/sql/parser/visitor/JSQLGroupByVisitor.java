package com.miljanilic.sql.parser.visitor;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.ast.expression.Expression;
import com.miljanilic.sql.ast.node.GroupBy;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.statement.select.GroupByElement;
import net.sf.jsqlparser.statement.select.GroupByVisitor;

@Singleton
public class JSQLGroupByVisitor implements GroupByVisitor<GroupBy> {
    private final ExpressionVisitor<Expression> expressionVisitor;

    @Inject
    public JSQLGroupByVisitor(ExpressionVisitor<Expression> expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public <S> GroupBy visit(GroupByElement groupByElement, S context) {
        if (!groupByElement.getGroupingSets().isEmpty()) {
            throw new UnsupportedOperationException("GROUPING SETS are not supported.");
        }

        ExpressionList<?> expressionList = (ExpressionList<?>) groupByElement.getGroupByExpressionList();
        return new GroupBy(expressionList.accept(expressionVisitor, context));
    }
}
