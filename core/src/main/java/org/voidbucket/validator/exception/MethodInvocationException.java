package org.voidbucket.validator.exception;

import lombok.Getter;
import org.voidbucket.validator.reflect.invoke.ConstraintInvoker;

public final class MethodInvocationException extends RuntimeException {

    @Getter
    public final ConstraintInvoker constraintInvoker;

    public MethodInvocationException(final ConstraintInvoker constraintInvoker,
                                     final String message) {
        super(message);
        this.constraintInvoker = constraintInvoker;
    }

    public MethodInvocationException(final ConstraintInvoker constraintInvoker,
                                     final String message,
                                     final Throwable cause) {
        super(message, cause);
        this.constraintInvoker = constraintInvoker;
    }

    public MethodInvocationException(final ConstraintInvoker constraintInvoker,
                                     final Throwable cause) {
        super(cause);
        this.constraintInvoker = constraintInvoker;
    }

}
