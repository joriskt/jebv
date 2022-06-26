package org.voidbucket.validator.reflect.invoke;

import java.lang.reflect.Method;

public final class InvocationRuntimeException extends AbstractInvocationException {

    public InvocationRuntimeException(final MethodInvoker invoker) {
        super(invoker);
    }

    public InvocationRuntimeException(final MethodInvoker invoker,
                                      final String message) {
        super(invoker, message);
    }

    public InvocationRuntimeException(final MethodInvoker invoker,
                                      final String message,
                                      final Throwable cause) {
        super(invoker, message, cause);
    }
}
