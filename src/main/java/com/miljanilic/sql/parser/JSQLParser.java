package com.miljanilic.sql.parser;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.ast.statement.Statement;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.SelectVisitor;

@Singleton
public class JSQLParser implements SQLParser {
    private final SelectVisitor<Statement> selectVisitor;

    @Inject
    public JSQLParser(SelectVisitor<Statement> selectVisitor) {
        this.selectVisitor = selectVisitor;
    }

    public Statement parse(String sql) {
        try {
            net.sf.jsqlparser.statement.Statement statement = CCJSqlParserUtil.parse(sql);

            if (statement instanceof net.sf.jsqlparser.statement.select.Select) {
                return visit((net.sf.jsqlparser.statement.select.Select) statement);
            }

            throw new UnsupportedOperationException("Only SELECT statements are supported.");
        } catch (JSQLParserException e) {
            throw new SQLParserException("Error parsing SQL: " + e.getMessage(), e);
        }
    }

    public Statement visit(net.sf.jsqlparser.statement.select.Select select) {
        return select.getPlainSelect().accept(this.selectVisitor, null);
    }
}
