package com.miljanilic.sql.deparser.visitor.table;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.deparser.visitor.base.BaseAstToJSqlJoinVisitor;

@Singleton
public class TableColumnNameAstToJSqlJoinVisitor extends BaseAstToJSqlJoinVisitor {

    @Inject
    public TableColumnNameAstToJSqlJoinVisitor(TableColumnNameAstToJSqlExpressionVisitor expressionVisitor, TableColumnNameAstToJSqlFromItemVisitor fromItemVisitor) {
        super(expressionVisitor, fromItemVisitor);
    }
}
