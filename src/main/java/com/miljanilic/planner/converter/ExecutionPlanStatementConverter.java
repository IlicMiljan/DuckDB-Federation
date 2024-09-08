package com.miljanilic.planner.converter;

import com.miljanilic.planner.node.PlanNode;
import com.miljanilic.sql.ast.statement.SelectStatement;

public interface ExecutionPlanStatementConverter {
    SelectStatement convert(PlanNode rootNode);
}
