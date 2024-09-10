package com.miljanilic.sql.parser;

import com.miljanilic.sql.ast.statement.Statement;

public interface SqlParser {
    Statement parse(String sql);
}
