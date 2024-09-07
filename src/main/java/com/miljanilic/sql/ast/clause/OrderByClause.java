package com.miljanilic.sql.ast.clause;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.node.OrderBy;

import java.util.List;
import java.util.stream.Collectors;

public class OrderByClause extends Clause {
    private final List<OrderBy> orderByList;

    public OrderByClause(List<OrderBy> orderBylist) {
        this.orderByList = orderBylist;
    }

    public List<OrderBy> getOrderByList() {
        return orderByList;
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
