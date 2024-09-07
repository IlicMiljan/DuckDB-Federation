package com.miljanilic.planner.node;

import com.miljanilic.planner.ExecutionPlanVisitor;
import com.miljanilic.sql.ast.expression.Expression;
import com.miljanilic.sql.ast.expression.Function;

import java.util.List;
import java.util.stream.Collectors;

public class AggregateNode extends PlanNode {
    private final List<Expression> groupByExpressions;
    private final List<Function> aggregateFunctions;

    public AggregateNode(List<Expression> groupByExpressions, List<Function> aggregateFunctions) {
        this.groupByExpressions = groupByExpressions;
        this.aggregateFunctions = aggregateFunctions;
    }

    public List<Expression> getGroupByExpressions() {
        return groupByExpressions;
    }

    public List<Function> getAggregateFunctions() {
        return aggregateFunctions;
    }

    @Override
    public <T, S> T accept(ExecutionPlanVisitor<T, S> visitor, S context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        String groupBy = groupByExpressions.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        String aggregates = aggregateFunctions.stream()
                .map(Function::toString)
                .collect(Collectors.joining(", "));
        return "Aggregate: " + (groupBy.isEmpty() ? "" : "GROUP BY " + groupBy + " ") +
                (aggregates.isEmpty() ? "" : "AGGREGATES: " + aggregates);
    }
}
