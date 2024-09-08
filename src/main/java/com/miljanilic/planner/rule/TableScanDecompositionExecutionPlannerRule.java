package com.miljanilic.planner.rule;

import com.miljanilic.planner.node.*;

public class TableScanDecompositionExecutionPlannerRule extends ExecutionPlannerRuleAdapter {
    @Override
    public PlanNode visit(ScanNode node, PlanNode context) {
        return new RemoteScanNode(
                node.getFrom(),
                node.getColumns(),
                node.getFrom().getSchema()
        );
    }
}
