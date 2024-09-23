package com.miljanilic.sql.deparser;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.deparser.visitor.simple.SimpleAstToJSqlSelectStatementVisitor;
import com.miljanilic.sql.deparser.visitor.table.TableColumnNameAstToJSqlSelectStatementVisitor;

@Singleton
public class SimpleJSqlDeParser extends BaseJSqlDeParser {
    @Inject
    public SimpleJSqlDeParser(SimpleAstToJSqlSelectStatementVisitor selectVisitor) {
        super(selectVisitor);
    }

}
