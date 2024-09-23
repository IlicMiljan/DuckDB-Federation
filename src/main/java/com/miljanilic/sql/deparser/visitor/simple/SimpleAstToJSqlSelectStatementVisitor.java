package com.miljanilic.sql.deparser.visitor.simple;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.deparser.visitor.base.BaseAstToJSqlSelectStatementVisitor;

@Singleton
public class SimpleAstToJSqlSelectStatementVisitor extends BaseAstToJSqlSelectStatementVisitor {

    @Inject
    public SimpleAstToJSqlSelectStatementVisitor(
            SimpleAstToJSqlSelectVisitor selectVisitor,
            SimpleAstToJSqlFromItemVisitor fromItemVisitor,
            SimpleAstToJSqlJoinVisitor joinVisitor,
            SimpleAstToJSqlExpressionVisitor expressionVisitor,
            SimpleAstToJSqlOrderByVisitor orderByVisitor,
            SimpleAstToJSqlGroupByVisitor groupByVisitor
    ) {
        super(selectVisitor, fromItemVisitor, joinVisitor, expressionVisitor, orderByVisitor, groupByVisitor);
    }
}
