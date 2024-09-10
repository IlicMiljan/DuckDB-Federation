package com.miljanilic;

import com.google.inject.Inject;
import com.miljanilic.catalog.data.Schema;
import com.miljanilic.planner.ExecutionPlanVisitor;
import com.miljanilic.planner.ExecutionPlanner;
import com.miljanilic.planner.converter.ExecutionPlanStatementConverter;
import com.miljanilic.planner.filter.ExecutionPlanAggregationNodeFilter;
import com.miljanilic.planner.filter.ExecutionPlanFilter;
import com.miljanilic.planner.filter.ExecutionPlanSchemaFilter;
import com.miljanilic.planner.node.PlanNode;
import com.miljanilic.planner.visitor.SchemaExtractingExecutionPlanVisitor;
import com.miljanilic.sql.ast.statement.SelectStatement;
import com.miljanilic.sql.ast.statement.Statement;
import com.miljanilic.sql.deparser.SqlDeParser;
import com.miljanilic.sql.parser.SqlParser;
import com.miljanilic.sql.parser.SQLParserException;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Application {
    private final SqlParser sqlParser;
    private final ExecutionPlanner executionPlanner;
    private final ExecutionPlanStatementConverter executionPlanStatementConverter;
    private final SqlDeParser sqlDeParser;

    @Inject
    public Application(
            SqlParser sqlParser,
            ExecutionPlanner executionPlanner,
            ExecutionPlanStatementConverter executionPlanStatementConverter,
            SqlDeParser sqlDeParser
    ) {
        this.sqlParser = sqlParser;
        this.executionPlanner = executionPlanner;
        this.executionPlanStatementConverter = executionPlanStatementConverter;
        this.sqlDeParser = sqlDeParser;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        String sql = scanner.nextLine();

        try {
            Statement statement = sqlParser.parse(sql);
            System.out.println("Parsed SQL: " + this.sqlDeParser.deparse(statement));


            System.out.println("----------------");

            PlanNode planRoot = executionPlanner.plan(statement);
            System.out.println(planRoot.explain());

            System.out.println("----------------");

            SelectStatement executionStatement = executionPlanStatementConverter.convert(planRoot);
            System.out.println("Execution SQL: " + this.sqlDeParser.deparse(executionStatement));

            System.out.println("----------------");

            ExecutionPlanVisitor<Void, Set<Schema>> schemaExtractor = new SchemaExtractingExecutionPlanVisitor();

            HashSet<Schema> schemas = new HashSet<>();
            planRoot.accept(schemaExtractor, schemas);

            for (Schema schema : schemas) {
                System.out.println(schema);
                System.out.println("----------------");
            }

            for (Schema schema : schemas) {
                ExecutionPlanFilter filter = new ExecutionPlanSchemaFilter(schema);

                PlanNode schemaFilteredPlanRoot = filter.filter(planRoot);

                SelectStatement schemaFilteredExecutionStatement = executionPlanStatementConverter.convert(schemaFilteredPlanRoot);
                System.out.println("Schema Filtered SQL (" + schema.getName() + "): " + this.sqlDeParser.deparse(schemaFilteredExecutionStatement));

                System.out.println("----------------");
            }

            ExecutionPlanFilter filter = new ExecutionPlanAggregationNodeFilter();

            PlanNode schemaFilteredPlanRoot = filter.filter(planRoot);

            SelectStatement schemaFilteredExecutionStatement = executionPlanStatementConverter.convert(schemaFilteredPlanRoot);

            System.out.println("Aggregation SQL: " + this.sqlDeParser.deparse(schemaFilteredExecutionStatement));

            System.out.println("----------------");
        } catch (SQLParserException e) {
            System.out.println("Error parsing SQL: " + e.getMessage());
        }
    }
}
