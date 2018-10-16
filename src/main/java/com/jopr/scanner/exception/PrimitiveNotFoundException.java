package com.jopr.scanner.exception;


public class PrimitiveNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -3317661742453989132L;

    public PrimitiveNotFoundException() {
        super();
    }

    public PrimitiveNotFoundException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PrimitiveNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrimitiveNotFoundException(String message) {
        super(message);
    }

    public PrimitiveNotFoundException(Throwable cause) {
        super(cause);
    }

}
