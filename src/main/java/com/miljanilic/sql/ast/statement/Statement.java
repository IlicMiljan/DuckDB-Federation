package com.miljanilic.sql.ast.statement;

import com.miljanilic.sql.ast.ASTNode;
import com.miljanilic.sql.ast.ASTVisitor;

public abstract class Statement implements ASTNode {
    @Override
    public abstract <T> T accept(ASTVisitor<T> visitor);
}
