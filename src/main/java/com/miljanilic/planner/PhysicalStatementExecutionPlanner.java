package com.miljanilic.planner;

import com.google.inject.Inject;
import com.miljanilic.planner.node.PlanNode;
import com.miljanilic.sql.ast.statement.Statement;

import java.util.List;

public class PhysicalStatementExecutionPlanner implements ExecutionPlanner {
    private final ExecutionPlanner planner;
    private final List<ExecutionPlanVisitor<PlanNode, PlanNode>> executionPlanVisitorList;

    @Inject
    public PhysicalStatementExecutionPlanner(
            ExecutionPlanner planner,
            List<ExecutionPlanVisitor<PlanNode, PlanNode>> planners
    ) {
        this.planner = planner;
        this.executionPlanVisitorList = planners;
    }

    @Override
    public PlanNode plan(Statement statement) {
        PlanNode finalPlan = planner.plan(statement);

        if (executionPlanVisitorList.isEmpty()) {
            throw new ExecutionPlannerException("Physical statement execution planner requires at least one rule configured.");
        }

        for (ExecutionPlanVisitor<PlanNode, PlanNode> visitor : executionPlanVisitorList) {
            finalPlan = finalPlan.accept(visitor, null);
        }

        return finalPlan;
    }
}
