package com.miljanilic.sql.ast.clause;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.node.Join;

import java.util.List;
import java.util.stream.Collectors;

public class JoinClause extends Clause {
    private final List<Join> joinList;

    public JoinClause(List<Join> joins) {
        this.joinList = joins;
    }

    public List<Join> getJoinList() {
        return joinList;
    }

    @Override
    public <T, S> T accept(ASTVisitor<T, S> visitor, S context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return joinList.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }
}
