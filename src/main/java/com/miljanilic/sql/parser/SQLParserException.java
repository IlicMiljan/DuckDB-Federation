package com.miljanilic.sql.parser;

public class SQLParserException extends RuntimeException {
    public SQLParserException(String message) {
        super(message);
    }

    public SQLParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
