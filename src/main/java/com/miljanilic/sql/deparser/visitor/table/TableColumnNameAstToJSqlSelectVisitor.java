package com.miljanilic.sql.deparser.visitor.table;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.deparser.visitor.base.BaseAstToJSqlSelectVisitor;

@Singleton
public class TableColumnNameAstToJSqlSelectVisitor extends BaseAstToJSqlSelectVisitor {

    @Inject
    public TableColumnNameAstToJSqlSelectVisitor(TableColumnNameAstToJSqlExpressionVisitor expressionVisitor) {
        super(expressionVisitor);
    }
}
