package com.miljanilic.sql.ast.node;

import com.miljanilic.sql.ast.ASTVisitor;

public class Table extends From {
    private final String name;
    private final String alias;

    public Table(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "TableNode{" +
                "name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                '}';
    }
}
