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
        return "SelectStatement{" +
                "selectClause=" + selectClause +
                ", fromClause=" + fromClause +
                ", joinClause=" + joinClause +
                ", whereClause=" + whereClause +
                ", groupByClause=" + groupByClause +
                ", havingClause=" + havingClause +
                ", orderByClause=" + orderByClause +
                ", limitClause=" + limitClause +
                '}';
    }
}
