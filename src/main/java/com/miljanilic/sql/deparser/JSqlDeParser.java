package com.miljanilic.sql.deparser;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.ast.statement.SelectStatement;
import com.miljanilic.sql.ast.statement.Statement;
import com.miljanilic.sql.deparser.visitor.AstToJSqlSelectStatementVisitor;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.util.deparser.StatementDeParser;

@Singleton
public class JSqlDeParser implements SqlDeParser {
    private final AstToJSqlSelectStatementVisitor selectStatementVisitor;

    @Inject
    public JSqlDeParser(AstToJSqlSelectStatementVisitor selectVisitor) {
        this.selectStatementVisitor = selectVisitor;
    }

    public String deparse(Statement statement) {
        StringBuilder builder = new StringBuilder();
        StatementDeParser deParser = new StatementDeParser(builder);


        if (statement instanceof SelectStatement) {
            deParser.visit(visit((SelectStatement) statement));

            return builder.toString();
        }

        throw new UnsupportedOperationException("Only SELECT statements are supported.");
    }

    public PlainSelect visit(SelectStatement select) {
        return select.accept(selectStatementVisitor, null);
    }
}
