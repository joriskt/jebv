package org.voidbucket.validator.reflect.invoke;

import org.voidbucket.validator.Context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AbstractMethodInvoker implements MethodInvoker {

    @Override
    public final Object invoke(final Object instance, Method method, final Context context)
            throws InvocationSubjectException, InvocationRuntimeException {
        // Perform the invocation using the subclasses' implementation.
        final Object result;
        try {
            result = _invoke(instance, method, context);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new InvocationSubjectException(
                this, "Failed to invoke method", ex);
        } catch (Throwable t) {
            throw new InvocationRuntimeException(this, "Method threw exception at runtime", t);
        }

        return SimpleInvocationResult.ofResult(result);
    }

    protected abstract Object _invoke(Object instance, Method method, Context context)
        throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;

}
