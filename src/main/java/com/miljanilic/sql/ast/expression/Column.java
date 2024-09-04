package com.miljanilic.sql.ast.expression;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.node.Table;

public class Column extends Expression {
    private final String name;
    private final Table table;

    public Column(String name, Table table) {
        this.name = name;
        this.table = table;
    }

    public String getName() {
        return name;
    }

    public Table getTable() {
        return table;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "ColumnExpression{" +
                "name='" + name + '\'' +
                ", table=" + table +
                '}';
    }
}
