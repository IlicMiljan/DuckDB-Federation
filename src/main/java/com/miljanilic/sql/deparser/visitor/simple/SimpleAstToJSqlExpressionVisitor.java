package com.miljanilic.sql.deparser.visitor.simple;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.deparser.visitor.base.BaseAstToJSqlExpressionVisitor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItem;

@Singleton
public class SimpleAstToJSqlExpressionVisitor extends BaseAstToJSqlExpressionVisitor {

    @Inject
    public SimpleAstToJSqlExpressionVisitor(SimpleAstToJSqlFromItemVisitor fromItemVisitor) {
        super(fromItemVisitor);
    }

    @Override
    public Expression visit(com.miljanilic.sql.ast.expression.Column column, Void context) {
        return new Column(
                null,
                column.getName()
        );
    }
}
