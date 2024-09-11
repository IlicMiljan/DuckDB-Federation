package com.miljanilic.sql.deparser.visitor;

import com.google.inject.Singleton;
import com.miljanilic.sql.ast.node.Table;
import com.miljanilic.sql.ast.visitor.ASTVisitorAdapter;
import net.sf.jsqlparser.statement.select.FromItem;

@Singleton
public class AstToJSqlFromItemVisitor extends ASTVisitorAdapter<FromItem, Void> {
    @Override
    public FromItem visit(Table table, Void context) {
        net.sf.jsqlparser.schema.Table jsqlTable = new net.sf.jsqlparser.schema.Table();

        jsqlTable.setSchemaName(table.getSchema().getName());
        jsqlTable.setName(table.getName());

        if (table.getAlias() != null) {
            jsqlTable.setAlias(new net.sf.jsqlparser.expression.Alias(table.getAlias()));
        }

        return jsqlTable;
    }
}