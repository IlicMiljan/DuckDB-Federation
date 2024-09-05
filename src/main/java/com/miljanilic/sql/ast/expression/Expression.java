package com.miljanilic.sql.ast.expression;

import com.miljanilic.sql.ast.ASTNode;
import com.miljanilic.sql.ast.ASTVisitor;

public abstract class Expression implements ASTNode {
    @Override
    public abstract <T> T accept(ASTVisitor<T> visitor);
}
