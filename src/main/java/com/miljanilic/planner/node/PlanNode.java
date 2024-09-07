package com.miljanilic.planner.node;

import com.miljanilic.planner.ExecutionPlanVisitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class PlanNode {
    protected List<PlanNode> children;

    public PlanNode() {
        this.children = new ArrayList<>();
    }

    public PlanNode(List<PlanNode> children) {
        this.children = children;
    }

    public void addChild(PlanNode child) {
        children.add(child);
    }

    public List<PlanNode> getChildren() {
        return children;
    }

    public void setChildren(List<PlanNode> children) {
        this.children = children;
    }

    public abstract <T, S> T accept(ExecutionPlanVisitor<T, S> visitor, S context);

    public String explain() {
        StringBuilder sb = new StringBuilder();
        explain(sb, "", "");

        return sb.toString();
    }

    private void explain(StringBuilder stringBuilder, String prefix, String childrenPrefix) {
        stringBuilder.append(prefix).append(this).append('\n');

        for (Iterator<PlanNode> it = children.iterator(); it.hasNext();) {
            PlanNode next = it.next();

            if (it.hasNext()) {
                next.explain(stringBuilder, childrenPrefix + "├── ", childrenPrefix + "│   ");
            } else {
                next.explain(stringBuilder, childrenPrefix + "└── ", childrenPrefix + "    ");
            }
        }
    }

    public abstract String toString();
}
