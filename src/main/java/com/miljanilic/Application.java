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
import com.miljanilic.sql.SqlDialect;
import com.miljanilic.sql.ast.statement.SelectStatement;
import com.miljanilic.sql.ast.statement.Statement;
import com.miljanilic.sql.deparser.SqlDeParserResolver;
import com.miljanilic.sql.parser.SqlParser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Application {
    private final SqlParser sqlParser;
    private final ExecutionPlanner executionPlanner;
    private final ExecutionPlanStatementConverter executionPlanStatementConverter;
    private final SqlDeParserResolver sqlDeParserResolver;
    private final ConcurrentSchemaQueryExecutor concurrentSchemaQueryExecutor;
    private final DuckDbTemporaryStorageResultSetConsumer duckDbTemporaryStorageResultSetConsumer;
    private final ResultSetPrinter resultSetPrinter;

    @Inject
    public Application(
            SqlParser sqlParser,
            ExecutionPlanner executionPlanner,
            ExecutionPlanStatementConverter executionPlanStatementConverter,
            SqlDeParserResolver sqlDeParserResolver,
            ConcurrentSchemaQueryExecutor concurrentSchemaQueryExecutor,
            DuckDbTemporaryStorageResultSetConsumer duckDbTemporaryStorageResultSetConsumer,
            ResultSetPrinter resultSetPrinter
    ) {
        this.sqlParser = sqlParser;
        this.executionPlanner = executionPlanner;
        this.executionPlanStatementConverter = executionPlanStatementConverter;
        this.sqlDeParserResolver = sqlDeParserResolver;
        this.concurrentSchemaQueryExecutor = concurrentSchemaQueryExecutor;
        this.duckDbTemporaryStorageResultSetConsumer = duckDbTemporaryStorageResultSetConsumer;
        this.resultSetPrinter = resultSetPrinter;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        String sql = scanner.nextLine();

        Instant start = Instant.now();

        try {
            Statement statement = sqlParser.parse(sql);
            System.out.println("Parsed SQL: " + this.sqlDeParserResolver.resolve(SqlDialect.DEFAULT).deparse(statement));


            System.out.println("----------------");

            PlanNode planRoot = executionPlanner.plan(statement);
            System.out.println(planRoot.explain());

            System.out.println("----------------");

            SelectStatement executionStatement = executionPlanStatementConverter.convert(planRoot);
            System.out.println("Execution SQL: " + this.sqlDeParserResolver.resolve(SqlDialect.DEFAULT).deparse(executionStatement));

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

                System.out.println("Schema Filtered SQL (" + schema.getName() + "): " + this.sqlDeParserResolver.resolve(schema.getDataSource().getDialect()).deparse(schemaFilteredExecutionStatement));

                System.out.println("----------------");
            }

            ExecutionPlanFilter filter = new ExecutionPlanAggregationNodeFilter();

            PlanNode schemaFilteredPlanRoot = filter.filter(planRoot);

            SelectStatement schemaFilteredExecutionStatement = executionPlanStatementConverter.convert(schemaFilteredPlanRoot);

            System.out.println("Aggregation SQL: " + this.sqlDeParserResolver.resolve(SqlDialect.DUCKDB).deparse(schemaFilteredExecutionStatement));

            System.out.println("----------------");

            try (Connection connection = DriverManager.getConnection("jdbc:duckdb:tmp/database.db")) {
                concurrentSchemaQueryExecutor.executeAll(schemaSelectStatementMap, (selectStatement, resultSet) -> {
                    duckDbTemporaryStorageResultSetConsumer.consume(connection, selectStatement, resultSet);
                });

                try (
                        java.sql.Statement stmt = connection.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT COUNT(DISTINCT oi_o.id) AS total_orders, SUM(oi_o.quantity * oi_o.price) AS total_order_value, AVG(oi_o.price) AS avg_price_per_product, COUNT(DISTINCT p.id) AS total_unique_products, COUNT(ua.id) AS total_activities, DATEDIFF('day', MAX(oi_o.order_date), MIN(oi_o.order_date)) AS days_between_first_and_last_order, ROUND(SUM(oi_o.quantity * oi_o.price) / COUNT(DISTINCT oi_o.id), 2) AS avg_order_value, ROUND(COUNT(ua.id) / DATEDIFF('day', MAX(oi_o.order_date), MIN(oi_o.order_date)), 2) AS avg_activities_per_day, ROUND(COUNT(DISTINCT oi_o.id) / DATEDIFF('day', MAX(oi_o.order_date), MIN(oi_o.order_date)), 2) AS orders_per_day, u.id AS user_id FROM order_items_orders oi_o INNER JOIN products p ON oi_o.product_id = p.id INNER JOIN users u ON oi_o.user_id = u.id LEFT JOIN user_activity ua ON u.id = ua.user_id GROUP BY u.id HAVING COUNT(DISTINCT oi_o.id) > 5 ORDER BY SUM(oi_o.quantity * oi_o.price) DESC LIMIT 10;")
                ) {
                    this.resultSetPrinter.print(rs);
                }
            }

        } catch (Exception e) {
            System.out.println("Error executing SQL: " + e.getMessage());
        }

        Instant finish = Instant.now();
        System.out.println("Time Elapsed: " + Duration.between(start, finish).toMillis() + "ms");
    }
}
