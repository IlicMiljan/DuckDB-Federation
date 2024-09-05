package com.miljanilic.sql.ast.clause;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.node.From;

public class FromClause extends Clause {
    public final From from;

    public FromClause(From from) {
        this.from = from;
    }

    @Override
    public <T, S> T accept(ASTVisitor<T, S> visitor, S context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return "FromClause{" +
                "from=" + from +
                '}';
    }
}
