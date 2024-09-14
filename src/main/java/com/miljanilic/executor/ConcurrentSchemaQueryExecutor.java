package com.miljanilic.executor;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.catalog.data.Schema;
import com.miljanilic.sql.ast.statement.SelectStatement;

import java.sql.ResultSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

@Singleton
public class ConcurrentSchemaQueryExecutor {
    private final SchemaQueryExecutor schemaQueryExecutor;

    @Inject
    public ConcurrentSchemaQueryExecutor(
            SchemaQueryExecutor schemaQueryExecutor
    ) {
        this.schemaQueryExecutor = schemaQueryExecutor;
    }

    public void executeAll(Map<Schema, SelectStatement> selectStatementMap, BiConsumer<SelectStatement, ResultSet> resultSetConsumer) {
        try (ExecutorService executorService = Executors.newFixedThreadPool(selectStatementMap.size())) {
            CountDownLatch latch = new CountDownLatch(selectStatementMap.size());

            for (Map.Entry<Schema, SelectStatement> entry : selectStatementMap.entrySet()) {
                executorService.submit(() -> {
                    try {
                        schemaQueryExecutor.execute(entry.getKey(), entry.getValue(), resultSetConsumer);
                    } catch (Exception e) {
                        throw new SQLExecutionException("Distributed query execution failed", e);
                    } finally {
                        latch.countDown();
                    }
                });
            }

            latch.await();
        } catch (Exception e) {
            throw new SQLExecutionException("Distributed query execution failed", e);
        }
    }
}
