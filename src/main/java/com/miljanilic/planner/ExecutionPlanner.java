package com.miljanilic.planner;

import com.miljanilic.planner.node.PlanNode;
import com.miljanilic.sql.ast.statement.Statement;

public interface ExecutionPlanner {
    PlanNode plan(Statement statement);
}
