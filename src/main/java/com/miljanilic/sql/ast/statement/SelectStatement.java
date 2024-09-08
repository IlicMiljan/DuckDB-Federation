package com.miljanilic.sql.ast.statement;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.clause.*;

public class SelectStatement extends Statement {
    private SelectClause selectClause;
    private FromClause fromClause;
    private JoinClause joinClause;
    private WhereClause whereClause;
    private GroupByClause groupByClause;
    private HavingClause havingClause;
    private OrderByClause orderByClause;
    private LimitClause limitClause;

    public SelectStatement() {}

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

    public void setSelectClause(SelectClause selectClause) {
        this.selectClause = selectClause;
    }

    public FromClause getFromClause() {
        return fromClause;
    }

    public void setFromClause(FromClause fromClause) {
        this.fromClause = fromClause;
    }

    public JoinClause getJoinClause() {
        return joinClause;
    }

    public void setJoinClause(JoinClause joinClause) {
        this.joinClause = joinClause;
    }

    public WhereClause getWhereClause() {
        return whereClause;
    }

    public void setWhereClause(WhereClause whereClause) {
        this.whereClause = whereClause;
    }

    public GroupByClause getGroupByClause() {
        return groupByClause;
    }

    public void setGroupByClause(GroupByClause groupByClause) {
        this.groupByClause = groupByClause;
    }

    public HavingClause getHavingClause() {
        return havingClause;
    }

    public void setHavingClause(HavingClause havingClause) {
        this.havingClause = havingClause;
    }

    public OrderByClause getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(OrderByClause orderByClause) {
        this.orderByClause = orderByClause;
    }

    public LimitClause getLimitClause() {
        return limitClause;
    }

    public void setLimitClause(LimitClause limitClause) {
        this.limitClause = limitClause;
    }

    @Override
    public <T, S> T accept(ASTVisitor<T, S> visitor, S context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SELECT ");

        sb.append(selectClause);
        sb.append("\n").append("FROM ").append(fromClause);
        if (joinClause != null) sb.append("\n").append(joinClause);
        if (whereClause != null) sb.append("\n").append(whereClause);
        if (groupByClause != null) sb.append("\n").append(groupByClause);
        if (havingClause != null) sb.append("\n").append(havingClause);
        if (orderByClause != null) sb.append("\n").append(orderByClause);
        if (limitClause != null) sb.append("\n").append(limitClause);

        return sb.toString();
    }
}
