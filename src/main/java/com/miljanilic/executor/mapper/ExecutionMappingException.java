package com.miljanilic.executor.mapper;

public class ExecutionMappingException extends RuntimeException {
    public ExecutionMappingException(String message) {
        super(message);
    }

    public ExecutionMappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
