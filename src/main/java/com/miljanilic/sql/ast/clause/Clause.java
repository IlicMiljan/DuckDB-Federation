package com.miljanilic.sql.ast.clause;

import com.miljanilic.sql.ast.ASTNode;
import com.miljanilic.sql.ast.ASTVisitor;

public abstract class Clause implements ASTNode {
    @Override
    public abstract <T, S> T accept(ASTVisitor<T, S> visitor, S context);
}
