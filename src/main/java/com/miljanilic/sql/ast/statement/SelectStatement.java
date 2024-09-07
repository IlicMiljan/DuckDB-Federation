package com.miljanilic.sql.ast.statement;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.clause.*;

public class SelectStatement extends Statement {
    private final SelectClause selectClause;
    private final FromClause fromClause;
    private final JoinClause joinClause;
    private final WhereClause whereClause;
    private final GroupByClause groupByClause;
    private final HavingClause havingClause;
    private final OrderByClause orderByClause;
    private final LimitClause limitClause;

    public SelectStatement(
            SelectClause selectClause,
            FromClause fromClause,
            JoinClause joinClause,
            WhereClause whereClause,
            GroupByClause groupByClause,
            HavingClause havingClause,
            OrderByClause orderByClause,
            LimitClause limitClause
    ) {
        this.selectClause = selectClause;
        this.fromClause = fromClause;
        this.joinClause = joinClause;
        this.whereClause = whereClause;
        this.groupByClause = groupByClause;
        this.havingClause = havingClause;
        this.orderByClause = orderByClause;
        this.limitClause = limitClause;
    }

    public SelectClause getSelectClause() {
        return selectClause;
    }

    public FromClause getFromClause() {
        return fromClause;
    }

    public JoinClause getJoinClause() {
        return joinClause;
    }

    public WhereClause getWhereClause() {
        return whereClause;
    }

    public GroupByClause getGroupByClause() {
        return groupByClause;
    }

    public HavingClause getHavingClause() {
        return havingClause;
    }

    public OrderByClause getOrderByClause() {
        return orderByClause;
    }

    public LimitClause getLimitClause() {
        return limitClause;
    }

    @Override
    public <T, S> T accept(ASTVisitor<T, S> visitor, S context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SELECT ");

        sb.append(selectClause).append("\n");
        sb.append("FROM ").append(fromClause).append("\n");
        if (joinClause != null) sb.append(joinClause).append("\n");
        if (whereClause != null) sb.append(whereClause).append("\n");
        if (groupByClause != null) sb.append(groupByClause).append("\n");
        if (havingClause != null) sb.append(havingClause).append("\n");
        if (orderByClause != null) sb.append(orderByClause).append("\n");
        if (limitClause != null) sb.append(limitClause);

        return sb.toString();
    }
}
