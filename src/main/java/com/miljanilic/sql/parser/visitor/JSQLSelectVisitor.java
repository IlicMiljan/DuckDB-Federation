package com.miljanilic.sql.parser.visitor;

import com.miljanilic.sql.ast.clause.*;
import com.miljanilic.sql.ast.expression.Expression;
import com.miljanilic.sql.ast.expression.ExpressionList;
import com.miljanilic.sql.ast.node.*;
import com.miljanilic.sql.ast.node.Join;
import com.miljanilic.sql.ast.node.Select;
import com.miljanilic.sql.ast.statement.SelectStatement;
import com.miljanilic.sql.ast.statement.Statement;
import com.miljanilic.sql.parser.resolver.JSQLJoinTypeResolver;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.statement.select.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JSQLSelectVisitor extends SelectVisitorAdapter<Statement> {
    private final SelectItemVisitor<Select> selectItemVisitor;
    private final FromItemVisitor<From> fromItemVisitor;
    private final GroupByVisitor<GroupBy> groupByVisitor;
    private final OrderByVisitor<OrderBy> orderByVisitor;
    private final ExpressionVisitor<Expression> expressionVisitor;
    private final JSQLJoinTypeResolver joinTypeResolver;

    public JSQLSelectVisitor(
            SelectItemVisitor<Select> selectItemVisitor,
            FromItemVisitor<From> fromItemVisitor,
            GroupByVisitor<GroupBy> groupByVisitor,
            OrderByVisitor<OrderBy> orderByVisitor,
            ExpressionVisitor<Expression> expressionVisitor,
            JSQLJoinTypeResolver joinTypeResolver
    ) {
        this.selectItemVisitor = selectItemVisitor;
        this.fromItemVisitor = fromItemVisitor;
        this.groupByVisitor = groupByVisitor;
        this.orderByVisitor = orderByVisitor;
        this.expressionVisitor = expressionVisitor;
        this.joinTypeResolver = joinTypeResolver;
    }

    @Override
    public <S> Statement visit(PlainSelect plainSelect, S context) {
        SelectClause selectClause = new SelectClause(this.visitSelectItems(plainSelect.getSelectItems(), context));
        FromClause fromClause = new FromClause(plainSelect.getFromItem().accept(this.fromItemVisitor, context));
        JoinClause joinClause = new JoinClause(this.visitJoinItems(plainSelect.getJoins(), context));
        WhereClause whereClause = new WhereClause(plainSelect.getWhere().accept(this.expressionVisitor, context));
        GroupByClause groupByClause = new GroupByClause(plainSelect.getGroupBy().accept(this.groupByVisitor, context));
        HavingClause havingClause = new HavingClause(plainSelect.getHaving().accept(this.expressionVisitor, context));
        OrderByClause orderByClause = new OrderByClause(this.visitOrderByElements(plainSelect.getOrderByElements(), context));
        LimitClause limitClause = new LimitClause(
                plainSelect.getLimit().getRowCount().accept(this.expressionVisitor, context),
                plainSelect.getLimit().getOffset() != null ? plainSelect.getLimit().getOffset().accept(this.expressionVisitor, context) : null
        );

        return new SelectStatement(
                selectClause,
                fromClause,
                joinClause,
                whereClause,
                groupByClause,
                havingClause,
                orderByClause,
                limitClause
        );
    }

    private <S> List<Select> visitSelectItems(List<SelectItem<?>> selectItems, S context) {
        return selectItems.stream()
                .map(item -> item.accept(this.selectItemVisitor, context))
                .collect(Collectors.toList());
    }

    private <S> List<Join> visitJoinItems(List<net.sf.jsqlparser.statement.select.Join> joins, S context) {
        return joins.stream()
                .map(join -> {
                    List<Expression> expressions = new ArrayList<>();

                    for (net.sf.jsqlparser.expression.Expression expression : join.getOnExpressions()) {
                        expressions.add(expression.accept(this.expressionVisitor, context));
                    }

                    return new SimpleJoin(
                            this.joinTypeResolver.resolveJoinType(join),
                            join.getFromItem().accept(this.fromItemVisitor, context),
                            new ExpressionList(expressions)
                    );
                })
                .collect(Collectors.toList());
    }

    private <S> List<OrderBy> visitOrderByElements(List<OrderByElement> orderByElements, S context) {
        return orderByElements.stream()
                .map(orderByElement -> orderByElement.accept(this.orderByVisitor, context))
                .toList();
    }
}
