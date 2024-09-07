package com.miljanilic.planner.node;

import com.miljanilic.planner.ExecutionPlanVisitor;
import com.miljanilic.sql.ast.node.From;

import java.util.List;

public class ScanNode extends PlanNode {
    private final From from;
    private final List<String> columns;

    public ScanNode(From table, List<String> columns) {
        this.from = table;
        this.columns = columns;
    }

    public From getFrom() {
        return from;
    }

    public List<String> getColumns() {
        return columns;
    }

    @Override
    public <T, S> T accept(ExecutionPlanVisitor<T, S> visitor, S context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return "Scan: " + from + (columns != null && !columns.isEmpty() ? " (Columns: " + String.join(", ", columns) + ")" : "");
    }
}
