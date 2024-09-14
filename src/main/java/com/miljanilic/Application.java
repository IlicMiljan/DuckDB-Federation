package com.miljanilic;

import com.google.inject.Inject;
import com.miljanilic.catalog.data.Schema;
import com.miljanilic.executor.ConcurrentSchemaQueryExecutor;
import com.miljanilic.executor.DuckDbTemporaryStorageResultSetConsumer;
import com.miljanilic.executor.ResultSetPrinter;
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.*;

public class Application {
    private final SqlParser sqlParser;
    private final ExecutionPlanner executionPlanner;
    private final ExecutionPlanStatementConverter executionPlanStatementConverter;
    private final SqlDeParser sqlDeParser;
    private final ConcurrentSchemaQueryExecutor concurrentSchemaQueryExecutor;
    private final DuckDbTemporaryStorageResultSetConsumer duckDbTemporaryStorageResultSetConsumer;
    private final ResultSetPrinter resultSetPrinter;

    @Inject
    public Application(
            SqlParser sqlParser,
            ExecutionPlanner executionPlanner,
            ExecutionPlanStatementConverter executionPlanStatementConverter,
            SqlDeParser sqlDeParser,
            ConcurrentSchemaQueryExecutor concurrentSchemaQueryExecutor,
            DuckDbTemporaryStorageResultSetConsumer duckDbTemporaryStorageResultSetConsumer,
            ResultSetPrinter resultSetPrinter
    ) {
        this.sqlParser = sqlParser;
        this.executionPlanner = executionPlanner;
        this.executionPlanStatementConverter = executionPlanStatementConverter;
        this.sqlDeParser = sqlDeParser;
        this.concurrentSchemaQueryExecutor = concurrentSchemaQueryExecutor;
        this.duckDbTemporaryStorageResultSetConsumer = duckDbTemporaryStorageResultSetConsumer;
        this.resultSetPrinter = resultSetPrinter;
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

            Map<Schema, SelectStatement> schemaSelectStatementMap = new HashMap<>();

            for (Schema schema : schemas) {
                ExecutionPlanFilter filter = new ExecutionPlanSchemaFilter(schema);

                PlanNode schemaFilteredPlanRoot = filter.filter(planRoot);

                SelectStatement schemaFilteredExecutionStatement = executionPlanStatementConverter.convert(schemaFilteredPlanRoot);
                schemaSelectStatementMap.put(schema, schemaFilteredExecutionStatement);

                System.out.println("Schema Filtered SQL (" + schema.getName() + "): " + this.sqlDeParser.deparse(schemaFilteredExecutionStatement));

                System.out.println("----------------");
            }

            ExecutionPlanFilter filter = new ExecutionPlanAggregationNodeFilter();

            PlanNode schemaFilteredPlanRoot = filter.filter(planRoot);

            SelectStatement schemaFilteredExecutionStatement = executionPlanStatementConverter.convert(schemaFilteredPlanRoot);

            System.out.println("Aggregation SQL: " + this.sqlDeParser.deparse(schemaFilteredExecutionStatement));

            System.out.println("----------------");

            try (Connection connection = DriverManager.getConnection("jdbc:duckdb:tmp/database.db")) {
                concurrentSchemaQueryExecutor.executeAll(schemaSelectStatementMap, (selectStatement, resultSet) -> {
                    duckDbTemporaryStorageResultSetConsumer.consume(connection, selectStatement, resultSet);
                });

                try (
                        java.sql.Statement stmt = connection.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables")
                ) {
                    this.resultSetPrinter.print(rs);
                }
            }

        } catch (Exception e) {
            System.out.println("Error executing SQL: " + e.getMessage());
        }
    }
}
