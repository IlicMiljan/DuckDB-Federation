package com.miljanilic.executor;

public class SQLExecutionException extends RuntimeException {
    public SQLExecutionException(String message) {
        super(message);
    }

    public SQLExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
