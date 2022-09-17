package org.voidbucket.validator.reflect.invoke;

public final class InvocationSubjectException extends AbstractInvocationException {

    public InvocationSubjectException(final ConstraintInvoker invoker) {
        super(invoker);
    }

    public InvocationSubjectException(final ConstraintInvoker invoker,
                                      final String message) {
        super(invoker, message);
    }

    public InvocationSubjectException(final ConstraintInvoker invoker,
                                      final String message,
                                      final Throwable cause) {
        super(invoker, message, cause);
    }

}
