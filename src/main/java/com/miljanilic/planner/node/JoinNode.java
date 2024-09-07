package com.miljanilic.planner.node;

import com.miljanilic.planner.ExecutionPlanVisitor;
import com.miljanilic.sql.ast.expression.Expression;
import com.miljanilic.sql.ast.node.Join;

import java.util.List;

public class JoinNode extends PlanNode {
    private final Join.JoinType joinType;
    private final Expression condition;
    private final JoinAlgorithm algorithm;

    public enum JoinAlgorithm {
        NESTED_LOOP, HASH_JOIN, MERGE_JOIN
    }

    public JoinNode(PlanNode left, PlanNode right, Join.JoinType joinType, Expression condition, JoinAlgorithm algorithm) {
        super(List.of(left, right));
        this.joinType = joinType;
        this.condition = condition;
        this.algorithm = algorithm;
    }

    public PlanNode getLeft() {
        return this.children.getFirst();
    }

    public PlanNode getRight() {
        return this.children.getLast();
    }

    public Join.JoinType getJoinType() {
        return joinType;
    }

    public Expression getCondition() {
        return condition;
    }

    public JoinAlgorithm getAlgorithm() {
        return algorithm;
    }

    @Override
    public <T, S> T accept(ExecutionPlanVisitor<T, S> visitor, S context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return joinType + " Join (" + algorithm + "): " + condition;
    }
}
