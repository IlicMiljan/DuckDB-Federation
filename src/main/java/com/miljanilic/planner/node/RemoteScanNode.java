package com.miljanilic.planner.node;

import com.miljanilic.catalog.data.Schema;
import com.miljanilic.sql.ast.node.From;

import java.util.List;

public class RemoteScanNode extends ScanNode {
    private final Schema schema;

    public RemoteScanNode(From from, List<String> columns, Schema schema) {
        super(from, columns);
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
