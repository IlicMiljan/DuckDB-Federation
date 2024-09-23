package com.miljanilic.sql.deparser.visitor.table;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.deparser.visitor.base.BaseAstToJSqlSelectStatementVisitor;

@Singleton
public class TableColumnNameAstToJSqlSelectStatementVisitor extends BaseAstToJSqlSelectStatementVisitor {

    @Inject
    public TableColumnNameAstToJSqlSelectStatementVisitor(
            TableColumnNameAstToJSqlSelectVisitor selectVisitor,
            TableColumnNameAstToJSqlFromItemVisitor fromItemVisitor,
            TableColumnNameAstToJSqlJoinVisitor joinVisitor,
            TableColumnNameAstToJSqlExpressionVisitor expressionVisitor,
            TableColumnNameAstToJSqlOrderByVisitor orderByVisitor,
            TableColumnNameAstToJSqlGroupByVisitor groupByVisitor
    ) {
        super(selectVisitor, fromItemVisitor, joinVisitor, expressionVisitor, orderByVisitor, groupByVisitor);
    }
}
