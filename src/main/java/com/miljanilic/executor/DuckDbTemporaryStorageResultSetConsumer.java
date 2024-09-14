package com.miljanilic.executor;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.executor.mapper.ColumnMapping;
import com.miljanilic.executor.mapper.ResultSetColumnIndexMapper;
import com.miljanilic.executor.table.DuckDbTemporaryTableCreator;
import com.miljanilic.executor.table.DuckDbTemporaryTableDefinitionGenerator;
import com.miljanilic.executor.table.TemporaryTable;
import com.miljanilic.sql.ast.expression.Column;
import com.miljanilic.sql.ast.statement.SelectStatement;
import com.miljanilic.sql.ast.visitor.ASTColumnExtractingVisitor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class DuckDbTemporaryStorageResultSetConsumer {
    private final ResultSetPrinter resultSetPrinter;
    private final ASTColumnExtractingVisitor columnExtractingVisitor;
    private final ResultSetColumnIndexMapper columnIndexMapper;
    private final DuckDbResultSetDataAppender duckDbResultSetDataAppender;
    private final DuckDbTemporaryTableDefinitionGenerator duckDbTemporaryTableDefinitionGenerator;
    private final DuckDbTemporaryTableCreator duckDbTemporaryTableCreator;

    @Inject
    public DuckDbTemporaryStorageResultSetConsumer(
            ResultSetPrinter resultSetPrinter,
            ASTColumnExtractingVisitor columnExtractingVisitor,
            ResultSetColumnIndexMapper columnIndexMapper,
            DuckDbResultSetDataAppender duckDbResultSetDataAppender,
            DuckDbTemporaryTableDefinitionGenerator duckDbTemporaryTableDefinitionGenerator,
            DuckDbTemporaryTableCreator duckDbTemporaryTableCreator
    ) {
        this.resultSetPrinter = resultSetPrinter;
        this.columnExtractingVisitor = columnExtractingVisitor;
        this.columnIndexMapper = columnIndexMapper;
        this.duckDbResultSetDataAppender = duckDbResultSetDataAppender;
        this.duckDbTemporaryTableDefinitionGenerator = duckDbTemporaryTableDefinitionGenerator;
        this.duckDbTemporaryTableCreator = duckDbTemporaryTableCreator;
    }

    public void consume(Connection connection, SelectStatement statement, ResultSet resultSet) {
        try {
            Set<Column> columns = new HashSet<>(statement.accept(this.columnExtractingVisitor, null));
            ColumnMapping columnMapping = this.columnIndexMapper.map(resultSet.getMetaData(), columns);
            TemporaryTable temporaryTable = this.duckDbTemporaryTableDefinitionGenerator.generate(columns);

            this.duckDbTemporaryTableCreator.create(connection, temporaryTable);
            this.duckDbResultSetDataAppender.append(connection, temporaryTable.getName(), resultSet, columnMapping);

            System.out.println("Data appended successfully into " + temporaryTable.getName());

            String selectSql = "SELECT * FROM " + temporaryTable.getName() + " LIMIT 10";

            try (
                    java.sql.Statement stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery(selectSql)
            ) {

                this.resultSetPrinter.print(rs);
            }
        } catch (Exception e) {
            throw new SQLExecutionException("An error occurred while executing SQL in DuckDB", e);
        }
    }
}
