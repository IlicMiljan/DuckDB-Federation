package com.miljanilic.sql.ast.clause;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.node.From;

public class FromClause extends Clause {
    public final From from;

    public FromClause(From from) {
        this.from = from;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "FromClause{" +
                "from=" + from +
                '}';
    }
}
