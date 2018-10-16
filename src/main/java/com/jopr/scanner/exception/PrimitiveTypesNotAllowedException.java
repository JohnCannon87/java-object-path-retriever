package com.jopr.scanner.exception;


public class PrimitiveTypesNotAllowedException extends Exception {

    private static final long serialVersionUID = 5976733439772619931L;

    public PrimitiveTypesNotAllowedException() {
        super();
    }

    public PrimitiveTypesNotAllowedException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PrimitiveTypesNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrimitiveTypesNotAllowedException(String message) {
        super(message);
    }

    public PrimitiveTypesNotAllowedException(Throwable cause) {
        super(cause);
    }

}
