package com.miljanilic.sql.deparser.visitor.fqcn;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.deparser.visitor.base.BaseAstToJSqlOrderByVisitor;

@Singleton
public class FullyQualifiedColumnNameAstToJSqlOrderByVisitor extends BaseAstToJSqlOrderByVisitor {

    @Inject
    public FullyQualifiedColumnNameAstToJSqlOrderByVisitor(FullyQualifiedColumnNameAstToJSqlExpressionVisitor expressionVisitor) {
        super(expressionVisitor);
    }
}
