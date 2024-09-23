package com.miljanilic.sql.deparser.visitor.simple;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.deparser.visitor.base.BaseAstToJSqlGroupByVisitor;

@Singleton
public class SimpleAstToJSqlGroupByVisitor extends BaseAstToJSqlGroupByVisitor {

    @Inject
    public SimpleAstToJSqlGroupByVisitor(SimpleAstToJSqlExpressionVisitor expressionVisitor) {
        super(expressionVisitor);
    }
}
