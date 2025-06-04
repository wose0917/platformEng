package com.warehouseserve.exception;

public class DataOperationException extends Exception {
    public DataOperationException(String message) {
        super(message);
    }

    public DataOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
