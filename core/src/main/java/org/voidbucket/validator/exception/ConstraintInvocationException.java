package org.voidbucket.validator.exception;

import lombok.Getter;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.reflect.MethodInvoker;

public final class ConstraintInvocationException extends AbstractConstraintException {

    @Getter
    public final MethodInvoker methodInvoker;

    public ConstraintInvocationException(final Constraint constraint,
                                         final MethodInvoker methodInvoker,
                                         final String message) {
        super(constraint, message);
        this.methodInvoker = methodInvoker;
    }

    public ConstraintInvocationException(final Constraint constraint,
                                         final MethodInvoker methodInvoker,
                                         final String message,
                                         final Throwable cause) {
        super(constraint, message, cause);
        this.methodInvoker = methodInvoker;
    }

    public ConstraintInvocationException(final Constraint constraint,
                                         final MethodInvoker methodInvoker,
                                         final Throwable cause) {
        super(constraint, cause);
        this.methodInvoker = methodInvoker;
    }

}
