package com.miljanilic;

import com.google.inject.Inject;
import com.miljanilic.planner.*;
import com.miljanilic.planner.node.PlanNode;
import com.miljanilic.sql.ast.statement.Statement;
import com.miljanilic.sql.parser.SQLParser;
import com.miljanilic.sql.parser.SQLParserException;

import java.util.Scanner;

public class Application {
    private final SQLParser sqlParser;
    private final ExecutionPlanner executionPlanner;

    @Inject
    public Application(
            SQLParser sqlParser,
            ExecutionPlanner executionPlanner
    ) {
        this.sqlParser = sqlParser;
        this.executionPlanner = executionPlanner;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        String sql = scanner.nextLine();

        try {
            Statement statement = sqlParser.parse(sql);
            System.out.println(statement);

            System.out.println("----------------");

            PlanNode planRoot = executionPlanner.plan(statement);
            System.out.println(planRoot.explain());
        } catch (SQLParserException e) {
            System.out.println("Error parsing SQL: " + e.getMessage());
        }
    }
}
