package com.miljanilic.planner.node;

import com.miljanilic.catalog.data.Schema;
import com.miljanilic.sql.ast.expression.Expression;
import com.miljanilic.sql.ast.node.Select;

import java.util.List;
import java.util.stream.Collectors;

public class RemoteProjectNode extends ProjectNode {
    private final Schema schema;

    public RemoteProjectNode(List<Select> selectList, Schema schema) {
        super(selectList);
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
