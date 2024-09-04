package com.miljanilic.sql.ast.node;

import com.miljanilic.sql.ast.ASTVisitor;

public abstract class Join extends Node {
    @Override
    public abstract <T> T accept(ASTVisitor<T> visitor);

    public enum JoinType {
        INNER, LEFT, RIGHT, FULL, CROSS
    }
}
