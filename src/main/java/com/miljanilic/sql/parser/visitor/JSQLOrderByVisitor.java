package com.miljanilic.sql.parser.visitor;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.ast.expression.Expression;
import com.miljanilic.sql.ast.node.OrderBy;
import com.miljanilic.sql.ast.node.SimpleOrderBy;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.OrderByVisitor;

@Singleton
public class JSQLOrderByVisitor implements OrderByVisitor<OrderBy> {
    private final ExpressionVisitor<Expression> expressionVisitor;

    @Inject
    public JSQLOrderByVisitor(ExpressionVisitor<Expression> expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public <S> OrderBy visit(OrderByElement orderByElement, S context) {
        if (orderByElement.getNullOrdering() != null) {
            throw new UnsupportedOperationException("NULL ordering in ORDER BY is not supported.");
        }

        return new SimpleOrderBy(orderByElement.getExpression().accept(this.expressionVisitor, context), orderByElement.isAsc());
    }
}
