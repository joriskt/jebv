package org.voidbucket.validator.exception;

public abstract class ValidatorException extends RuntimeException {

    public ValidatorException(final String message) {
        super(message);
    }

    public ValidatorException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ValidatorException(final Throwable cause) {
        super(cause);
    }

}
