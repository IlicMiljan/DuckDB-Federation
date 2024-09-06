package com.miljanilic.catalog;

public class FailedLoadingSchemaException extends RuntimeException {
    public FailedLoadingSchemaException(String message) {
        super(message);
    }

    public FailedLoadingSchemaException(String message, Throwable cause) {
        super(message, cause);
    }
}
