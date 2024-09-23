package com.miljanilic.sql.deparser.visitor.base;

import com.miljanilic.sql.ast.node.OrderBy;
import com.miljanilic.sql.ast.node.Select;
import com.miljanilic.sql.ast.statement.SelectStatement;
import com.miljanilic.sql.ast.visitor.ASTVisitorAdapter;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.Limit;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAstToJSqlSelectStatementVisitor extends ASTVisitorAdapter<PlainSelect, Void> {
    private final BaseAstToJSqlSelectVisitor selectVisitor;
    private final BaseAstToJSqlFromItemVisitor fromItemVisitor;
    private final BaseAstToJSqlJoinVisitor joinVisitor;
    private final BaseAstToJSqlExpressionVisitor expressionVisitor;
    private final BaseAstToJSqlOrderByVisitor orderByVisitor;
    private final BaseAstToJSqlGroupByVisitor groupByVisitor;

    public BaseAstToJSqlSelectStatementVisitor(
            BaseAstToJSqlSelectVisitor selectVisitor,
            BaseAstToJSqlFromItemVisitor fromItemVisitor,
            BaseAstToJSqlJoinVisitor joinVisitor,
            BaseAstToJSqlExpressionVisitor expressionVisitor,
            BaseAstToJSqlOrderByVisitor orderByVisitor,
            BaseAstToJSqlGroupByVisitor groupByVisitor
    ) {
        this.selectVisitor = selectVisitor;
        this.fromItemVisitor = fromItemVisitor;
        this.joinVisitor = joinVisitor;
        this.expressionVisitor = expressionVisitor;
        this.orderByVisitor = orderByVisitor;
        this.groupByVisitor = groupByVisitor;
    }

    @Override
    public PlainSelect visit(SelectStatement selectStatement, Void context) {
        PlainSelect plainSelect = new PlainSelect();

        for (Select select : selectStatement.getSelectClause().getSelectList()) {
            plainSelect.addSelectItem(select.accept(selectVisitor, null));
        }

        plainSelect.setFromItem(selectStatement.getFromClause().getFrom().accept(fromItemVisitor, null));

        if (selectStatement.getJoinClause() != null) {
            List<Join> joins = new ArrayList<>();

            for (com.miljanilic.sql.ast.node.Join join : selectStatement.getJoinClause().getJoinList()) {
                joins.add(join.accept(joinVisitor, null));
            }

            plainSelect.setJoins(joins);
        }

        if (selectStatement.getWhereClause() != null) {
            plainSelect.setWhere(selectStatement.getWhereClause().getCondition().accept(expressionVisitor, null));
        }

        if (selectStatement.getGroupByClause() != null) {
            plainSelect.setGroupByElement(selectStatement.getGroupByClause().getGroupBy().accept(groupByVisitor, null));
        }

        if (selectStatement.getHavingClause() != null) {
            plainSelect.setHaving(selectStatement.getHavingClause().getCondition().accept(expressionVisitor, null));
        }

        if (selectStatement.getOrderByClause() != null) {
            List<OrderByElement> orderByElements = new ArrayList<>();

            for (OrderBy orderBy : selectStatement.getOrderByClause().getOrderByList()) {
                orderByElements.add(orderBy.accept(orderByVisitor, null));
            }

            plainSelect.setOrderByElements(orderByElements);
        }

        if (selectStatement.getLimitClause() != null) {
            Limit limit = new Limit();

            limit.setRowCount(selectStatement.getLimitClause().getLimit().accept(expressionVisitor, null));

            if (selectStatement.getLimitClause().getOffset() != null) {
                limit.setOffset(selectStatement.getLimitClause().getOffset().accept(expressionVisitor, null));
            }

            plainSelect.setLimit(limit);
        }

        return plainSelect;
    }
}
