package com.miljanilic.sql.deparser.visitor.fqcn;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.deparser.visitor.base.BaseAstToJSqlSelectVisitor;

@Singleton
public class FullyQualifiedColumnNameAstToJSqlSelectVisitor extends BaseAstToJSqlSelectVisitor {

    @Inject
    public FullyQualifiedColumnNameAstToJSqlSelectVisitor(FullyQualifiedColumnNameAstToJSqlExpressionVisitor expressionVisitor) {
        super(expressionVisitor);
    }
}
