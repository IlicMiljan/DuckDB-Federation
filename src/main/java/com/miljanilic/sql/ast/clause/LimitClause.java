package com.miljanilic.sql.ast.clause;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.expression.Expression;

public class LimitClause extends Clause {
    private Expression limit;
    private Expression offset;

    public LimitClause(final Expression limit, final Expression offset) {
        this.limit = limit;
        this.offset = offset;
    }

    public Expression getLimit() {
        return limit;
    }

    public void setLimit(Expression limit) {
        this.limit = limit;
    }

    public Expression getOffset() {
        return offset;
    }

    public void setOffset(Expression offset) {
        this.offset = offset;
    }

    @Override
    public <T, S> T accept(ASTVisitor<T, S> visitor, S context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("LIMIT ");
        sb.append(limit);

        if (offset != null) {
            sb.append(" OFFSET ").append(offset);
        }

        return sb.toString();
    }
}
