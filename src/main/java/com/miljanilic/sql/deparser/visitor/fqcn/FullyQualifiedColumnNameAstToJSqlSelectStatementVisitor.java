package com.miljanilic.sql.deparser.visitor.fqcn;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.deparser.visitor.base.BaseAstToJSqlSelectStatementVisitor;

@Singleton
public class FullyQualifiedColumnNameAstToJSqlSelectStatementVisitor extends BaseAstToJSqlSelectStatementVisitor {

    @Inject
    public FullyQualifiedColumnNameAstToJSqlSelectStatementVisitor(
            FullyQualifiedColumnNameAstToJSqlSelectVisitor selectVisitor,
            FullyQualifiedColumnNameAstToJSqlFromItemVisitor fromItemVisitor,
            FullyQualifiedColumnNameAstToJSqlJoinVisitor joinVisitor,
            FullyQualifiedColumnNameAstToJSqlExpressionVisitor expressionVisitor,
            FullyQualifiedColumnNameAstToJSqlOrderByVisitor orderByVisitor,
            FullyQualifiedColumnNameAstToJSqlGroupByVisitor groupByVisitor
    ) {
        super(selectVisitor, fromItemVisitor, joinVisitor, expressionVisitor, orderByVisitor, groupByVisitor);
    }
}
