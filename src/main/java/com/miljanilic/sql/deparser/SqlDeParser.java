package com.miljanilic.sql.deparser;

import com.miljanilic.sql.ast.statement.Statement;

public interface SqlDeParser {
    String deparse(Statement statement);
}
