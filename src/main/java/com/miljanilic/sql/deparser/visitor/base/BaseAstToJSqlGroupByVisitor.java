package com.miljanilic.sql.deparser.visitor.base;

import com.miljanilic.sql.ast.node.GroupBy;
import com.miljanilic.sql.ast.visitor.ASTVisitorAdapter;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.statement.select.GroupByElement;

public abstract class BaseAstToJSqlGroupByVisitor extends ASTVisitorAdapter<GroupByElement, Void> {
    protected final BaseAstToJSqlExpressionVisitor expressionVisitor;

    public BaseAstToJSqlGroupByVisitor(BaseAstToJSqlExpressionVisitor expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public GroupByElement visit(GroupBy groupBy, Void context) {
        GroupByElement jsqlGroupByElement = new GroupByElement();

        ExpressionList<?> expressionList = new ExpressionList<>(
                groupBy.getExpression().accept(expressionVisitor, null)
        );

        jsqlGroupByElement.addGroupByExpressions(expressionList);

        return jsqlGroupByElement;
    }
}
