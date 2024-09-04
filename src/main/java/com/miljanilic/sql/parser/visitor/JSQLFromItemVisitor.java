package com.miljanilic.sql.parser.visitor;

import com.miljanilic.sql.ast.node.From;
import com.miljanilic.sql.ast.node.Table;
import net.sf.jsqlparser.statement.select.FromItemVisitorAdapter;

public class JSQLFromItemVisitor extends FromItemVisitorAdapter<From> {
    @Override
    public <S> From visit(net.sf.jsqlparser.schema.Table table, S context) {
        return new Table(
                table.getName(),
                table.getAlias() != null ? table.getAlias().getName() : null
        );
    }
}
