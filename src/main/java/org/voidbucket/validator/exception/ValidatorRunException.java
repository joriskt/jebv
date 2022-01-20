package org.voidbucket.validator.exception;

public class ValidatorRunException extends ValidatorException {

    public ValidatorRunException(String message) {
        super(message);
    }
    public ValidatorRunException(String message, Throwable cause) {
        super(message, cause);
    }
    public ValidatorRunException(Throwable cause) {
        super(cause);
    }

}
