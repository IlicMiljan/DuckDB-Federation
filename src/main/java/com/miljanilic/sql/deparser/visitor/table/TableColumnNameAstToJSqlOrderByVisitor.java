package com.miljanilic.sql.deparser.visitor.table;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.deparser.visitor.base.BaseAstToJSqlOrderByVisitor;

@Singleton
public class TableColumnNameAstToJSqlOrderByVisitor extends BaseAstToJSqlOrderByVisitor {

    @Inject
    public TableColumnNameAstToJSqlOrderByVisitor(TableColumnNameAstToJSqlExpressionVisitor expressionVisitor) {
        super(expressionVisitor);
    }
}
