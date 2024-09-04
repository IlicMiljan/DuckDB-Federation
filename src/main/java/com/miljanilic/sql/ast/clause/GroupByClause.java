package com.miljanilic.sql.ast.clause;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.node.GroupBy;
import com.miljanilic.sql.ast.node.Node;

public class GroupByClause extends Clause {
    private final GroupBy groupBy;

    public GroupByClause(GroupBy groupBy) {
        this.groupBy = groupBy;
    }

    public Node getGroupBy() {
        return groupBy;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "GroupByClause{" +
                "groupBy=" + groupBy +
                '}';
    }
}
