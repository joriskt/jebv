package org.voidbucket.validator.exception;

import lombok.Getter;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.reflect.invoke.MethodInvoker;

public final class MethodInvocationException extends RuntimeException {

    @Getter
    public final MethodInvoker methodInvoker;

    public MethodInvocationException(final MethodInvoker methodInvoker,
                                     final String message) {
        super(message);
        this.methodInvoker = methodInvoker;
    }

    public MethodInvocationException(final MethodInvoker methodInvoker,
                                     final String message,
                                     final Throwable cause) {
        super(message, cause);
        this.methodInvoker = methodInvoker;
    }

    public MethodInvocationException(final MethodInvoker methodInvoker,
                                     final Throwable cause) {
        super(cause);
        this.methodInvoker = methodInvoker;
    }

}
