package com.miljanilic.sql.deparser.visitor.simple;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.deparser.visitor.base.BaseAstToJSqlJoinVisitor;

@Singleton
public class SimpleAstToJSqlJoinVisitor extends BaseAstToJSqlJoinVisitor {

    @Inject
    public SimpleAstToJSqlJoinVisitor(SimpleAstToJSqlExpressionVisitor expressionVisitor, SimpleAstToJSqlFromItemVisitor fromItemVisitor) {
        super(expressionVisitor, fromItemVisitor);
    }
}
