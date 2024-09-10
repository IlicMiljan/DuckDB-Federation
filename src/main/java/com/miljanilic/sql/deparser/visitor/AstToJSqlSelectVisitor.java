package com.miljanilic.sql.deparser.visitor;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.ast.node.Select;
import com.miljanilic.sql.ast.visitor.ASTVisitorAdapter;
import net.sf.jsqlparser.expression.Expression;

@Singleton
public class AstToJSqlSelectVisitor extends ASTVisitorAdapter<Expression, Void> {
    private final AstToJSqlExpressionVisitor expressionVisitor;

    @Inject
    public AstToJSqlSelectVisitor(AstToJSqlExpressionVisitor expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public Expression visit(Select table, Void context) {
        return table.getExpression().accept(expressionVisitor, context);
    }
}
