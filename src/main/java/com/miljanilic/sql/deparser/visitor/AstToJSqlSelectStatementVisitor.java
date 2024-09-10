package com.miljanilic.sql.deparser.visitor;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.ast.node.OrderBy;
import com.miljanilic.sql.ast.node.Select;
import com.miljanilic.sql.ast.statement.SelectStatement;
import com.miljanilic.sql.ast.visitor.ASTVisitorAdapter;
import net.sf.jsqlparser.statement.select.*;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class AstToJSqlSelectStatementVisitor extends ASTVisitorAdapter<PlainSelect, Void> {
    private final AstToJSqlSelectVisitor selectVisitor;
    private final AstToJSqlFromItemVisitor fromItemVisitor;
    private final AstToJSqlJoinVisitor joinVisitor;
    private final AstToJSqlExpressionVisitor expressionVisitor;
    private final AstToJSqlOrderByVisitor orderByVisitor;
    private final AstToJSqlGroupByVisitor groupByVisitor;

    @Inject
    public AstToJSqlSelectStatementVisitor(
            AstToJSqlSelectVisitor selectVisitor,
            AstToJSqlFromItemVisitor fromItemVisitor,
            AstToJSqlJoinVisitor joinVisitor,
            AstToJSqlExpressionVisitor expressionVisitor,
            AstToJSqlOrderByVisitor orderByVisitor,
            AstToJSqlGroupByVisitor groupByVisitor
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
