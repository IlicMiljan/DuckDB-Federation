package com.miljanilic.sql.deparser.visitor.base;

import com.miljanilic.sql.ast.node.Select;
import com.miljanilic.sql.ast.visitor.ASTVisitorAdapter;
import net.sf.jsqlparser.expression.Expression;

public abstract class BaseAstToJSqlSelectVisitor extends ASTVisitorAdapter<Expression, Void> {
    private final BaseAstToJSqlExpressionVisitor expressionVisitor;

    public BaseAstToJSqlSelectVisitor(BaseAstToJSqlExpressionVisitor expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public Expression visit(Select table, Void context) {
        return table.getExpression().accept(expressionVisitor, context);
    }
}
