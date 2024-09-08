package com.miljanilic.sql.ast.clause;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.node.OrderBy;

import java.util.List;
import java.util.stream.Collectors;

public class OrderByClause extends Clause {
    private List<OrderBy> orderByList;

    public OrderByClause(List<OrderBy> orderBylist) {
        this.orderByList = orderBylist;
    }

    public void addOrderBy(OrderBy orderBy) {
        orderByList.add(orderBy);
    }

    public List<OrderBy> getOrderByList() {
        return orderByList;
    }

    public void setOrderByList(List<OrderBy> orderByList) {
        this.orderByList = orderByList;
    }

    @Override
    public <T, S> T accept(ASTVisitor<T, S> visitor, S context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return "ORDER BY " + orderByList.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }
}
