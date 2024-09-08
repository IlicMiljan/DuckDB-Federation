package com.miljanilic.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.miljanilic.planner.*;
import com.miljanilic.planner.converter.ExecutionPlanStatementConverter;
import com.miljanilic.planner.converter.ExecutionPlanVisitorStatementConverter;
import com.miljanilic.planner.node.PlanNode;
import com.miljanilic.planner.rule.JoinDecompositionExecutionPlannerRule;
import com.miljanilic.planner.rule.ProjectDecompositionExecutionPlannerRule;
import com.miljanilic.planner.rule.TableScanDecompositionExecutionPlannerRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PlannerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ExecutionPlanner.class).to(PhysicalStatementExecutionPlanner.class);
        bind(ExecutionPlanStatementConverter.class).to(ExecutionPlanVisitorStatementConverter.class);

        Multibinder<ExecutionPlanVisitor<PlanNode, PlanNode>> plannerBinder = Multibinder.newSetBinder(
                binder(),
                new TypeLiteral<ExecutionPlanVisitor<PlanNode, PlanNode>>() {}
        );

        plannerBinder.addBinding().to(TableScanDecompositionExecutionPlannerRule.class);
        plannerBinder.addBinding().to(JoinDecompositionExecutionPlannerRule.class);
        plannerBinder.addBinding().to(ProjectDecompositionExecutionPlannerRule.class);
    }

    @Provides
    public List<ExecutionPlanVisitor<PlanNode, PlanNode>> provideExecutionPlanVisitors(
            Set<ExecutionPlanVisitor<PlanNode, PlanNode>> plannerSet
    ) {
        return new ArrayList<>(plannerSet);
    }

    @Provides
    public PhysicalStatementExecutionPlanner providePhysicalStatementExecutionPlanner(
            LogicQueryExecutionPlanner logicQueryExecutionPlanner,
            List<ExecutionPlanVisitor<PlanNode, PlanNode>> planners
    ) {
        return new PhysicalStatementExecutionPlanner(
                logicQueryExecutionPlanner,
                planners
        );
    }
}
