package com.miljanilic.planner.node;

import com.miljanilic.catalog.data.Schema;
import com.miljanilic.sql.ast.expression.Expression;

public class RemoteFilterNode extends FilterNode {
    private final Schema schema;

    public RemoteFilterNode(Expression condition, Schema schema) {
        super(condition);
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
