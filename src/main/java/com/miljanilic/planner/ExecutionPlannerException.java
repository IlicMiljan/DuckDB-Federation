package com.miljanilic.planner;

public class ExecutionPlannerException extends RuntimeException {
    public ExecutionPlannerException(String message) {
        super(message);
    }

    public ExecutionPlannerException(String message, Throwable cause) {
        super(message, cause);
    }
}
