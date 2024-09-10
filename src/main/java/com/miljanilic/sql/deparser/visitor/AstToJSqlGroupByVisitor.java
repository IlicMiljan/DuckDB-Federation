package com.miljanilic.sql.deparser.visitor;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.ast.node.GroupBy;
import com.miljanilic.sql.ast.visitor.ASTVisitorAdapter;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.statement.select.GroupByElement;

@Singleton
public class AstToJSqlGroupByVisitor extends ASTVisitorAdapter<GroupByElement, Void> {
    private final AstToJSqlExpressionVisitor expressionVisitor;

    @Inject
    public AstToJSqlGroupByVisitor(AstToJSqlExpressionVisitor expressionVisitor) {
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
