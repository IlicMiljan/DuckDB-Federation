package com.miljanilic.sql.deparser.visitor;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.ast.node.OrderBy;
import com.miljanilic.sql.ast.visitor.ASTVisitorAdapter;
import net.sf.jsqlparser.statement.select.OrderByElement;

@Singleton
public class AstToJSqlOrderByVisitor extends ASTVisitorAdapter<OrderByElement, Void> {

    private final AstToJSqlExpressionVisitor expressionVisitor;

    @Inject
    public AstToJSqlOrderByVisitor(AstToJSqlExpressionVisitor expressionVisitor) {
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
