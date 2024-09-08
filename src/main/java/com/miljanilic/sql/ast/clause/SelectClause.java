package com.miljanilic.sql.ast.clause;

import com.miljanilic.sql.ast.ASTVisitor;
import com.miljanilic.sql.ast.node.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SelectClause extends Clause {
    private List<Select> selectList;

    public SelectClause() {
        this.selectList = new ArrayList<>();
    }

    public SelectClause(List<Select> selectList) {
        this.selectList = selectList;
    }

    public void addSelect(Select select) {
        selectList.add(select);
    }

    public List<Select> getSelectList() {
        return selectList;
    }

    public void setSelectList(List<Select> selectList) {
        this.selectList = selectList;
    }

    @Override
    public <T, S> T accept(ASTVisitor<T, S> visitor, S context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return selectList.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }
}
