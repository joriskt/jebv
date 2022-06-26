package org.voidbucket.validator.reflect.invoke;

public class AbstractInvocationException extends RuntimeException {

    private final MethodInvoker invoker;

    public AbstractInvocationException(final MethodInvoker invoker) {
        super();
        this.invoker = invoker;
    }

    public AbstractInvocationException(final MethodInvoker invoker,
                                       final String message) {
        super(message);
        this.invoker = invoker;
    }

    public AbstractInvocationException(final MethodInvoker invoker,
                                       final String message,
                                       final Throwable cause) {
        super(message, cause);
        this.invoker = invoker;
    }

}
