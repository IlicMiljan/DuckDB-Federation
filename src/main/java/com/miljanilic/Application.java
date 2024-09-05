package com.miljanilic;

import com.google.inject.Inject;
import com.miljanilic.sql.ast.statement.SelectStatement;
import com.miljanilic.sql.ast.statement.Statement;
import com.miljanilic.sql.parser.SQLParser;
import com.miljanilic.sql.parser.SQLParserException;

import java.util.Scanner;

public class Application {
    private final SQLParser sqlParser;

    @Inject
    public Application(SQLParser sqlParser) {
        this.sqlParser = sqlParser;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        String sql = scanner.nextLine();

        try {
            Statement statement = sqlParser.parse(sql);

            if (statement instanceof SelectStatement) {
                System.out.println(statement);
            }
        } catch (SQLParserException e) {
            System.out.println("Error parsing SQL: " + e.getMessage());
        }
    }
}
