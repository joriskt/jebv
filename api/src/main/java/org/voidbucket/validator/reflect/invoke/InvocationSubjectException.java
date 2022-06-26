package org.voidbucket.validator.reflect.invoke;

import java.lang.reflect.Method;

public final class InvocationSubjectException extends AbstractInvocationException {

    public InvocationSubjectException(final MethodInvoker invoker) {
        super(invoker);
    }

    public InvocationSubjectException(final MethodInvoker invoker,
                                      final String message) {
        super(invoker, message);
    }

    public InvocationSubjectException(final MethodInvoker invoker,
                                      final String message,
                                      final Throwable cause) {
        super(invoker, message, cause);
    }

}
