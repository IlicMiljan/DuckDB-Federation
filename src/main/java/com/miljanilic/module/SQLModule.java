package com.miljanilic.module;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.miljanilic.sql.ast.expression.Expression;
import com.miljanilic.sql.ast.node.From;
import com.miljanilic.sql.ast.node.GroupBy;
import com.miljanilic.sql.ast.node.OrderBy;
import com.miljanilic.sql.ast.node.Select;
import com.miljanilic.sql.ast.statement.Statement;
import com.miljanilic.sql.parser.JSQLParser;
import com.miljanilic.sql.parser.SQLParser;
import com.miljanilic.sql.parser.visitor.*;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.statement.select.*;

public class SQLModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(SQLParser.class).to(JSQLParser.class);

        bind(new TypeLiteral<SelectVisitor<Statement>>() {}).to(JSQLSelectVisitor.class);
        bind(new TypeLiteral<SelectItemVisitor<Select>>() {}).to(JSQLSelectItemVisitor.class);
        bind(new TypeLiteral<FromItemVisitor<From>>() {}).to(JSQLFromItemVisitor.class);
        bind(new TypeLiteral<GroupByVisitor<GroupBy>>() {}).to(JSQLGroupByVisitor.class);
        bind(new TypeLiteral<OrderByVisitor<OrderBy>>() {}).to(JSQLOrderByVisitor.class);
        bind(new TypeLiteral<ExpressionVisitor<Expression>>() {}).to(JSQLExpressionVisitor.class);
    }
}
