package com.miljanilic.sql.ast.clause;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.expression.Expression;

public class LimitClause extends Clause {
    private final Expression limit;
    private final Expression offset;

    public LimitClause(final Expression limit, final Expression offset) {
        this.limit = limit;
        this.offset = offset;
    }

    public Expression getLimit() {
        return limit;
    }

    public Expression getOffset() {
        return offset;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "LimitClause{" +
                "limit=" + limit +
                ", offset=" + offset +
                '}';
    }
}