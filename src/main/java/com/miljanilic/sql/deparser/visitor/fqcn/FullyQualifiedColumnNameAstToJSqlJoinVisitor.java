package com.miljanilic.sql.deparser.visitor.fqcn;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.deparser.visitor.base.BaseAstToJSqlJoinVisitor;

@Singleton
public class FullyQualifiedColumnNameAstToJSqlJoinVisitor extends BaseAstToJSqlJoinVisitor {

    @Inject
    public FullyQualifiedColumnNameAstToJSqlJoinVisitor(
            FullyQualifiedColumnNameAstToJSqlExpressionVisitor expressionVisitor,
            FullyQualifiedColumnNameAstToJSqlFromItemVisitor fromItemVisitor
    ) {
        super(expressionVisitor, fromItemVisitor);
    }
}
