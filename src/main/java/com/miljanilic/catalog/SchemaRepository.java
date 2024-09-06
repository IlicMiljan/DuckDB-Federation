package com.miljanilic.catalog;

import com.miljanilic.catalog.data.Schema;
import com.miljanilic.catalog.data.Table;

import java.util.List;

public interface SchemaRepository {
    // Fetch all schemas
    List<Schema> getSchemas();

    // Fetch a specific schema by name
    Schema getSchema(String schemaName);

    // Fetch all tables in a specific schema
    List<Table> getTables(String schemaName);

    // Fetch a specific table or view by name in a specific schema
    Table getTable(String schemaName, String tableName);
}
