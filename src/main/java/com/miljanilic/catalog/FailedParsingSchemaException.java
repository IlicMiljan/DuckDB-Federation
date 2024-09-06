package com.miljanilic.catalog;

public class FailedParsingSchemaException extends RuntimeException {

    public FailedParsingSchemaException(String message) {
        super(message);
    }

    public FailedParsingSchemaException(String message, Throwable cause) {
        super(message, cause);
    }
}
