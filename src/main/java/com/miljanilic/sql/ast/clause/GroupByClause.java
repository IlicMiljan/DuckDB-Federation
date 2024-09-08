package com.miljanilic.sql.ast.clause;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.node.GroupBy;

public class GroupByClause extends Clause {
    private GroupBy groupBy;

    public GroupByClause(GroupBy groupBy) {
        this.groupBy = groupBy;
    }

    public GroupBy getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(GroupBy groupBy) {
        this.groupBy = groupBy;
    }

    @Override
    public <T, S> T accept(ASTVisitor<T, S> visitor, S context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return "GROUP BY " + groupBy;
    }
}
