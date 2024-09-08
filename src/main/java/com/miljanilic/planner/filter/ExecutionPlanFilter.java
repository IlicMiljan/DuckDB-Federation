package com.miljanilic.planner.filter;

import com.miljanilic.planner.node.PlanNode;

public interface ExecutionPlanFilter {
    PlanNode filter(PlanNode rootNode);
}
