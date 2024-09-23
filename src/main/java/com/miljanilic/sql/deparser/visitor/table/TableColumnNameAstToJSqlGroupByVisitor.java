package com.miljanilic.sql.deparser.visitor.table;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.deparser.visitor.base.BaseAstToJSqlGroupByVisitor;

@Singleton
public class TableColumnNameAstToJSqlGroupByVisitor extends BaseAstToJSqlGroupByVisitor {

    @Inject
    public TableColumnNameAstToJSqlGroupByVisitor(TableColumnNameAstToJSqlExpressionVisitor expressionVisitor) {
        super(expressionVisitor);
    }
}
