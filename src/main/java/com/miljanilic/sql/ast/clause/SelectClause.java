package com.miljanilic.sql.ast.clause;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.node.Select;

import java.util.List;

public class SelectClause extends Clause {
    private final List<Select> selectList;

    public SelectClause(List<Select> selectList) {
        this.selectList = selectList;
    }

    public List<Select> getSelectList() {
        return selectList;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "SelectClause{" +
                "selectList=" + selectList +
                '}';
    }
}
