package com.miljanilic.sql.ast.clause;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.node.Join;

import java.util.List;

public class JoinClause extends Clause {
    private final List<Join> joins;

    public JoinClause(List<Join> joins) {
        this.joins = joins;
    }

    public List<Join> getJoins() {
        return joins;
    }

    @Override
    public <T, S> T accept(ASTVisitor<T, S> visitor, S context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return "JoinClause{" +
                "joins=" + joins +
                '}';
    }
}
