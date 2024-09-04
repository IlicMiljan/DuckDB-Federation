package com.miljanilic.sql.ast;

public interface ASTNode {
    <T> T accept(ASTVisitor<T> visitor);
}
