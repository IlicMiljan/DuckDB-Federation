package com.miljanilic.sql.ast.clause;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.node.OrderBy;

import java.util.List;

public class OrderByClause extends Clause {
    private final List<OrderBy> orderByList;

    public OrderByClause(List<OrderBy> orderBylist) {
        this.orderByList = orderBylist;
    }

    public List<OrderBy> getOrderByList() {
        return orderByList;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "OrderByClause{" +
                "orderByList=" + orderByList +
                '}';
    }
}
