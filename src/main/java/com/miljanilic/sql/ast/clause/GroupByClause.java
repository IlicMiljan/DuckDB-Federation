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
    public <T, S> T accept(ASTVisitor<T, S> visitor, S context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return "GroupByClause{" +
                "groupBy=" + groupBy +
                '}';
    }
}
