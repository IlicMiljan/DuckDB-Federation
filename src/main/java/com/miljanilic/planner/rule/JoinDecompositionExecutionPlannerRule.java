package com.miljanilic.planner.rule;

import com.miljanilic.planner.node.JoinNode;
import com.miljanilic.planner.node.PlanNode;
import com.miljanilic.planner.node.RemoteJoinNode;
import com.miljanilic.planner.node.RemoteScanNode;

import java.util.List;

public class JoinDecompositionExecutionPlannerRule extends ExecutionPlannerRuleAdapter {
    @Override
    public PlanNode visit(JoinNode node, PlanNode context) {
        List<PlanNode> children = visitChildren(node.getChildren(), context);

        if (children.getFirst() instanceof RemoteScanNode leftScan && children.getLast() instanceof RemoteScanNode rightScan) {
            if (leftScan.getSchema().equals(rightScan.getSchema())) {
                RemoteJoinNode remoteJoinNode = new RemoteJoinNode(
                        node.getLeft(),
                        node.getRight(),
                        node.getJoinType(),
                        node.getCondition(),
                        node.getAlgorithm(),
                        leftScan.getSchema()
                );
                remoteJoinNode.setChildren(children);

                return remoteJoinNode;
            }
        }

        node.setChildren(children);
        return node;
    }
}
