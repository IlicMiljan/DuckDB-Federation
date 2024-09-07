package com.miljanilic.sql.parser.visitor;

import com.google.inject.Inject;
import com.google.inject.Singleton;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class JSQLSelectVisitor extends SelectVisitorAdapter<Statement> {
    private final SelectItemVisitor<Select> selectItemVisitor;
    private final FromItemVisitor<From> fromItemVisitor;
    private final GroupByVisitor<GroupBy> groupByVisitor;
    private final OrderByVisitor<OrderBy> orderByVisitor;
    private final ExpressionVisitor<Expression> expressionVisitor;
    private final JSQLJoinTypeResolver joinTypeResolver;

    @Inject
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
        FromClause fromClause = Optional.ofNullable(plainSelect.getFromItem())
                .map(item -> new FromClause(item.accept(this.fromItemVisitor, context)))
                .orElse(null);
        JoinClause joinClause = new JoinClause(this.visitJoinItems(plainSelect.getJoins(), context));
        WhereClause whereClause = Optional.ofNullable(plainSelect.getWhere())
                .map(where -> new WhereClause(where.accept(this.expressionVisitor, context)))
                .orElse(null);
        GroupByClause groupByClause = Optional.ofNullable(plainSelect.getGroupBy())
                .map(groupBy -> new GroupByClause(groupBy.accept(this.groupByVisitor, context)))
                .orElse(null);
        HavingClause havingClause = Optional.ofNullable(plainSelect.getHaving())
                .map(having -> new HavingClause(having.accept(this.expressionVisitor, context)))
                .orElse(null);
        OrderByClause orderByClause = new OrderByClause(this.visitOrderByElements(plainSelect.getOrderByElements(), context));
        LimitClause limitClause = this.createLimitClause(plainSelect.getLimit(), context);

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
        return Optional.ofNullable(selectItems)
                .orElse(Collections.emptyList())
                .stream()
                .map(item -> item.accept(this.selectItemVisitor, context))
                .collect(Collectors.toList());
    }

    private <S> List<Join> visitJoinItems(List<net.sf.jsqlparser.statement.select.Join> joins, S context) {
        return Optional.ofNullable(joins)
                .orElse(Collections.emptyList())
                .stream()
                .map(join -> {
                    List<Expression> expressions = Optional.ofNullable(join.getOnExpressions())
                            .orElse(Collections.emptyList())
                            .stream()
                            .map(expression -> expression.accept(this.expressionVisitor, context))
                            .collect(Collectors.toList());

                    return new Join(
                            this.joinTypeResolver.resolveJoinType(join),
                            Optional.ofNullable(join.getFromItem())
                                    .map(item -> item.accept(this.fromItemVisitor, context))
                                    .orElse(null),
                            new ExpressionList(expressions)
                    );
                })
                .collect(Collectors.toList());
    }

    private <S> List<OrderBy> visitOrderByElements(List<OrderByElement> orderByElements, S context) {
        return Optional.ofNullable(orderByElements)
                .orElse(Collections.emptyList())
                .stream()
                .map(orderByElement -> orderByElement.accept(this.orderByVisitor, context))
                .collect(Collectors.toList());
    }

    private <S> LimitClause createLimitClause(Limit limit, S context) {
        if (limit == null) {
            return null;
        }

        Expression rowCount = Optional.ofNullable(limit.getRowCount())
                .map(count -> count.accept(this.expressionVisitor, context))
                .orElse(null);

        Expression offset = Optional.ofNullable(limit.getOffset())
                .map(off -> off.accept(this.expressionVisitor, context))
                .orElse(null);

        return new LimitClause(rowCount, offset);
    }
}