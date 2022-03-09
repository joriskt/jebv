package org.voidbucket.validator.exception;

import lombok.Getter;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintInvoker;

public final class ConstraintInvocationException extends AbstractConstraintException {

    @Getter
    public final ConstraintInvoker constraintInvoker;

    public ConstraintInvocationException(final Constraint constraint,
                                         final ConstraintInvoker constraintInvoker,
                                         final String message) {
        super(constraint, message);
        this.constraintInvoker = constraintInvoker;
    }

    public ConstraintInvocationException(final Constraint constraint,
                                         final ConstraintInvoker constraintInvoker,
                                         final String message,
                                         final Throwable cause) {
        super(constraint, message, cause);
        this.constraintInvoker = constraintInvoker;
    }

    public ConstraintInvocationException(final Constraint constraint,
                                         final ConstraintInvoker constraintInvoker,
                                         final Throwable cause) {
        super(constraint, cause);
        this.constraintInvoker = constraintInvoker;
    }

}
