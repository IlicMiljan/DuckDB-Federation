package com.miljanilic.sql.parser;

import com.miljanilic.sql.ast.expression.Expression;
import com.miljanilic.sql.ast.node.*;
import com.miljanilic.sql.ast.node.Select;
import com.miljanilic.sql.ast.statement.Statement;
import com.miljanilic.sql.parser.resolver.JSQLJoinTypeResolver;
import com.miljanilic.sql.parser.visitor.*;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.*;

public class JSQLParser implements SQLParser {

    public Statement parse(String sql) {
        try {
            net.sf.jsqlparser.statement.Statement statement = CCJSqlParserUtil.parse(sql);

            if (statement instanceof net.sf.jsqlparser.statement.select.Select) {
                return visit((net.sf.jsqlparser.statement.select.Select) statement);
            }

            throw new UnsupportedOperationException("Only SELECT statements are supported.");
        } catch (JSQLParserException e) {
            throw new SQLParserException("Error parsing SQL: " + e.getMessage(), e);
        }
    }

    public Statement visit(net.sf.jsqlparser.statement.select.Select select) {
        PlainSelect plainSelect = select.getPlainSelect();

        FromItemVisitor<From> fromItemVisitor = new JSQLFromItemVisitor();
        ExpressionVisitor<Expression> expressionVisitor = new JSQLExpressionVisitor(fromItemVisitor);
        SelectItemVisitor<Select> selectItemVisitor = new JSQLSelectItemVisitor(expressionVisitor);
        GroupByVisitor<GroupBy> groupByVisitor = new JSQLGroupByVisitor(expressionVisitor);
        OrderByVisitor<OrderBy> orderByVisitor = new JSQLOrderByVisitor(expressionVisitor);
        JSQLJoinTypeResolver joinTypeResolver = new JSQLJoinTypeResolver();
        SelectVisitor<Statement> selectVisitor = new JSQLSelectVisitor(
                selectItemVisitor,
                fromItemVisitor,
                groupByVisitor,
                orderByVisitor,
                expressionVisitor,
                joinTypeResolver
        );

        return plainSelect.accept(selectVisitor, null);
    }
}
