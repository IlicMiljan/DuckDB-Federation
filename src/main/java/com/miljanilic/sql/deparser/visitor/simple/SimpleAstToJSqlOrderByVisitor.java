package com.miljanilic.sql.deparser.visitor.simple;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.deparser.visitor.base.BaseAstToJSqlOrderByVisitor;

@Singleton
public class SimpleAstToJSqlOrderByVisitor extends BaseAstToJSqlOrderByVisitor {

    @Inject
    public SimpleAstToJSqlOrderByVisitor(SimpleAstToJSqlExpressionVisitor expressionVisitor) {
        super(expressionVisitor);
    }
}
