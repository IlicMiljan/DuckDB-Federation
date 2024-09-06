package com.miljanilic.catalog;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.catalog.data.Schema;
import com.miljanilic.catalog.data.Table;
import com.miljanilic.catalog.loader.SchemaLoader;

import java.util.List;

@Singleton
public class InMemorySchemaRepository implements SchemaRepository {
    private final SchemaLoader schemaLoader;

    @Inject
    public InMemorySchemaRepository(SchemaLoader schemaLoader) {
        this.schemaLoader = schemaLoader;
    }

    @Override
    public List<Schema> getSchemas() {
        return schemaLoader.getSchemaList();
    }

    @Override
    public Schema getSchema(String schemaName) {
        return schemaLoader.getSchemaList().stream()
                .filter(schema -> schema.getName().equalsIgnoreCase(schemaName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Table> getTables(String schemaName) {
        Schema schema = getSchema(schemaName);
        return schema != null ? schema.getTables() : null;
    }

    @Override
    public Table getTable(String schemaName, String tableName) {
        Schema schema = getSchema(schemaName);
        if (schema != null) {
            return schema.getTables().stream()
                    .filter(table -> table.getName().equalsIgnoreCase(tableName))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}

