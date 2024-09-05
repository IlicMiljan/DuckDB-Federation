package com.miljanilic.sql.ast.node;

import com.miljanilic.sql.ast.ASTVisitor;

public abstract class From extends Node {
    @Override
    public abstract <T, S> T accept(ASTVisitor<T, S> visitor, S context);
}