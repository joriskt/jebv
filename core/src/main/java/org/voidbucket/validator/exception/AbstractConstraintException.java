package org.voidbucket.validator.exception;

import lombok.Getter;
import org.voidbucket.validator.constraint.Constraint;

abstract class AbstractConstraintException extends ValidatorException {

    @Getter
    private final Constraint constraint;

    public AbstractConstraintException(final Constraint constraint,
                                       final String message) {
        super(message);
        this.constraint = constraint;
    }

    public AbstractConstraintException(final Constraint constraint,
                                       final String message,
                                       final Throwable cause) {
        super(message, cause);
        this.constraint = constraint;
    }

    public AbstractConstraintException(final Constraint constraint,
                                       final Throwable cause) {
        super(cause);
        this.constraint = constraint;
    }

}
