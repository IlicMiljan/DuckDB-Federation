package com.miljanilic.sql.deparser;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.deparser.visitor.fqcn.FullyQualifiedColumnNameAstToJSqlSelectStatementVisitor;

@Singleton
public class FullyQualifiedColumnNameJSqlDeParser extends BaseJSqlDeParser {

    @Inject
    public FullyQualifiedColumnNameJSqlDeParser(
            FullyQualifiedColumnNameAstToJSqlSelectStatementVisitor selectVisitor
    ) {
        super(selectVisitor);
    }
}
