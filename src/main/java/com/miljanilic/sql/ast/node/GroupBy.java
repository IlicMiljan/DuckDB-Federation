package com.miljanilic.sql.ast.node;

import com.miljanilic.sql.ast.ASTVisitor;

public abstract class GroupBy extends Node {
    @Override
    public abstract <T> T accept(ASTVisitor<T> visitor);
}
