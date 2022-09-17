package org.voidbucket.validator.reflect.invoke;

public final class InvocationRuntimeException extends AbstractInvocationException {

    public InvocationRuntimeException(final ConstraintInvoker invoker) {
        super(invoker);
    }

    public InvocationRuntimeException(final ConstraintInvoker invoker,
                                      final String message) {
        super(invoker, message);
    }

    public InvocationRuntimeException(final ConstraintInvoker invoker,
                                      final String message,
                                      final Throwable cause) {
        super(invoker, message, cause);
    }
}
