package com.miljanilic.planner.node;

import com.miljanilic.planner.ExecutionPlanVisitor;
import com.miljanilic.sql.ast.node.Select;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectNode extends PlanNode {
    private final List<Select> selectList;

    public ProjectNode(List<Select> selectList) {
        this.selectList = selectList;
    }

    public List<Select> getSelectList() {
        return selectList;
    }

    @Override
    public <T, S> T accept(ExecutionPlanVisitor<T, S> visitor, S context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return "Project: " + selectList.stream()
                .map(select -> (select).getExpression() +
                        ((select).getAlias() != null ? " AS " + (select).getAlias() : ""))
                .collect(Collectors.joining(", "));
    }
}
