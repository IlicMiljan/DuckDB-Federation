package com.miljanilic.planner.node;

import com.miljanilic.catalog.data.Schema;
import com.miljanilic.sql.ast.expression.Expression;
import com.miljanilic.sql.ast.node.Join;

public class RemoteJoinNode extends JoinNode {
    private final Schema schema;

    public RemoteJoinNode(PlanNode left, PlanNode right, Join.JoinType joinType, Expression condition, JoinAlgorithm algorithm, Schema schema) {
        super(left, right, joinType, condition, algorithm);
        this.schema = schema;
    }

    public Schema getSchema() {
        return schema;
    }

    @Override
    public String toString() {
        return "Remote (" + schema.getName() + ") " + super.toString();
    }
}
