package com.miljanilic.sql.ast;

public interface ASTNode {
    <T, S> T accept(ASTVisitor<T, S> visitor, S context);
}
