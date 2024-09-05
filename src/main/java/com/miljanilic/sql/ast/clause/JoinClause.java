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
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "JoinClause{" +
                "joins=" + joins +
                '}';
    }
}
