package org.voidbucket.validator.reflect.invoke;

public class AbstractInvocationException extends RuntimeException {

    private final ConstraintInvoker invoker;

    public AbstractInvocationException(final ConstraintInvoker invoker) {
        super();
        this.invoker = invoker;
    }

    public AbstractInvocationException(final ConstraintInvoker invoker,
                                       final String message) {
        super(message);
        this.invoker = invoker;
    }

    public AbstractInvocationException(final ConstraintInvoker invoker,
                                       final String message,
                                       final Throwable cause) {
        super(message, cause);
        this.invoker = invoker;
    }

}
