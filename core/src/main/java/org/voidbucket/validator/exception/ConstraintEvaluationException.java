package org.voidbucket.validator.exception;

import org.voidbucket.validator.constraint.Constraint;

public final class ConstraintEvaluationException extends AbstractConstraintException {

    public ConstraintEvaluationException(final Constraint constraint,
                                         final String message) {
        super(constraint, message);
    }

    public ConstraintEvaluationException(final Constraint constraint,
                                         final String message,
                                         final Throwable cause) {
        super(constraint, message, cause);
    }

    public ConstraintEvaluationException(final Constraint constraint,
                                         final Throwable cause) {
        super(constraint, cause);
    }

}
