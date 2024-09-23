package com.miljanilic.sql.deparser;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.deparser.visitor.table.TableColumnNameAstToJSqlSelectStatementVisitor;

@Singleton
public class TableColumnNameJSqlDeParser extends BaseJSqlDeParser {
    @Inject
    public TableColumnNameJSqlDeParser(TableColumnNameAstToJSqlSelectStatementVisitor selectVisitor) {
        super(selectVisitor);
    }

}
