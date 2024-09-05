package com.miljanilic.sql.parser;

import com.miljanilic.sql.ast.statement.Statement;

public interface SQLParser {
    Statement parse(String sql);
}
