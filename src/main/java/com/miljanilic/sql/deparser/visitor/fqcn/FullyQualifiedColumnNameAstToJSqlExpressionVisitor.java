package com.miljanilic.sql.deparser.visitor.fqcn;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.deparser.visitor.base.BaseAstToJSqlExpressionVisitor;

@Singleton
public class FullyQualifiedColumnNameAstToJSqlExpressionVisitor extends BaseAstToJSqlExpressionVisitor {

    @Inject
    public FullyQualifiedColumnNameAstToJSqlExpressionVisitor(FullyQualifiedColumnNameAstToJSqlFromItemVisitor fromItemVisitor) {
        super(fromItemVisitor);
    }
}
