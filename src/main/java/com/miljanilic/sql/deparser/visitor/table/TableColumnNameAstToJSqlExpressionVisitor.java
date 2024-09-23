package com.miljanilic.sql.deparser.visitor.table;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.deparser.visitor.base.BaseAstToJSqlExpressionVisitor;

@Singleton
public class TableColumnNameAstToJSqlExpressionVisitor extends BaseAstToJSqlExpressionVisitor {

    @Inject
    public TableColumnNameAstToJSqlExpressionVisitor(TableColumnNameAstToJSqlFromItemVisitor fromItemVisitor) {
        super(fromItemVisitor);
    }

}
