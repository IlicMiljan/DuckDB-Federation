package com.miljanilic.sql.deparser.visitor.base;

import com.miljanilic.sql.ast.node.OrderBy;
import com.miljanilic.sql.ast.visitor.ASTVisitorAdapter;
import net.sf.jsqlparser.statement.select.OrderByElement;

public abstract class BaseAstToJSqlOrderByVisitor extends ASTVisitorAdapter<OrderByElement, Void> {

    private final BaseAstToJSqlExpressionVisitor expressionVisitor;

    public BaseAstToJSqlOrderByVisitor(BaseAstToJSqlExpressionVisitor expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public OrderByElement visit(OrderBy orderBy, Void context) {
        OrderByElement element = new OrderByElement();

        element.setExpression(orderBy.getExpression().accept(expressionVisitor, null));
        element.setAsc(orderBy.isAscending());

        return element;
    }
}
