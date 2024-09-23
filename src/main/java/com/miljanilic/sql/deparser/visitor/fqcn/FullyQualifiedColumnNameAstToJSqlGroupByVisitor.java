package com.miljanilic.sql.deparser.visitor.fqcn;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.deparser.visitor.base.BaseAstToJSqlGroupByVisitor;

@Singleton
public class FullyQualifiedColumnNameAstToJSqlGroupByVisitor extends BaseAstToJSqlGroupByVisitor {

    @Inject
    public FullyQualifiedColumnNameAstToJSqlGroupByVisitor(FullyQualifiedColumnNameAstToJSqlExpressionVisitor expressionVisitor) {
        super(expressionVisitor);
    }
}
