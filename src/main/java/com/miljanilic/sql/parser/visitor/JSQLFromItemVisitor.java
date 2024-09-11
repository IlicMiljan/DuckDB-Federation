package com.miljanilic.sql.parser.visitor;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.catalog.SchemaRepository;
import com.miljanilic.sql.ast.node.From;
import com.miljanilic.sql.ast.node.Table;
import net.sf.jsqlparser.statement.select.FromItemVisitorAdapter;

@Singleton
public class JSQLFromItemVisitor extends FromItemVisitorAdapter<From> {
    private final SchemaRepository schemaRepository;

    @Inject
    public JSQLFromItemVisitor(SchemaRepository schemaRepository) {
        this.schemaRepository = schemaRepository;
    }

    @Override
    public <S> From visit(net.sf.jsqlparser.schema.Table table, S context) {
        return new Table(
                this.schemaRepository.getSchema(table.getSchemaName()),
                this.schemaRepository.getSchemaTable(table.getSchemaName(), table.getName()),
                table.getName(),
                table.getAlias() != null ? table.getAlias().getName() : null
        );
    }
}
