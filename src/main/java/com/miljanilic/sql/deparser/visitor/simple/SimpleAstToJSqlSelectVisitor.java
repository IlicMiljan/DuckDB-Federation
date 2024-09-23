package com.miljanilic.sql.deparser.visitor.simple;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.deparser.visitor.base.BaseAstToJSqlSelectVisitor;

@Singleton
public class SimpleAstToJSqlSelectVisitor extends BaseAstToJSqlSelectVisitor {

    @Inject
    public SimpleAstToJSqlSelectVisitor(SimpleAstToJSqlExpressionVisitor expressionVisitor) {
        super(expressionVisitor);
    }
}
