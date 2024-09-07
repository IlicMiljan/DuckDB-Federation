package com.miljanilic.module;

import com.google.inject.AbstractModule;
import com.miljanilic.planner.ExecutionPlanner;
import com.miljanilic.planner.LogicQueryExecutionPlanner;

public class PlannerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ExecutionPlanner.class).to(LogicQueryExecutionPlanner.class);
    }
}
