package org.voidbucket.validator.exception;

public class ValidatorConfigurationException extends ValidatorException {

    public ValidatorConfigurationException(final String message) {
        super(message);
    }

    public ValidatorConfigurationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ValidatorConfigurationException(final Throwable cause) {
        super(cause);
    }

}
