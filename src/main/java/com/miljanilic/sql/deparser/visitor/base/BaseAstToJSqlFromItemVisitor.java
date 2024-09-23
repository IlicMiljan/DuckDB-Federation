package com.miljanilic.sql.deparser.visitor.base;

import com.miljanilic.sql.ast.node.Table;
import com.miljanilic.sql.ast.visitor.ASTVisitorAdapter;
import net.sf.jsqlparser.statement.select.FromItem;

public abstract class BaseAstToJSqlFromItemVisitor extends ASTVisitorAdapter<FromItem, Void> {
    @Override
    public FromItem visit(Table table, Void context) {
        net.sf.jsqlparser.schema.Table jsqlTable = new net.sf.jsqlparser.schema.Table();

        jsqlTable.setName(table.getName());

        if (table.getAlias() != null) {
            jsqlTable.setAlias(new net.sf.jsqlparser.expression.Alias(table.getAlias()));
        }

        return jsqlTable;
    }
}
