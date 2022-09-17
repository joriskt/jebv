package org.voidbucket.validator.reflect.invoke;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.voidbucket.validator.Context;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintStatus;
import org.voidbucket.validator.exception.result.ConstraintReadinessException;
import org.voidbucket.validator.exception.result.ConstraintStatusException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public abstract class AbstractConstraintInvoker implements ConstraintInvoker {

    @Override
    public final @NotNull ConstraintStatus invoke(final Constraint constraint, final Context context)
            throws InvocationSubjectException, InvocationRuntimeException {
        // Perform the invocation using the subclasses' implementation.
        final Object result;
        try {
            result = _invoke(constraint.getInstance(), constraint.getMethod(), context);
        } catch (IllegalAccessException | IllegalArgumentException ex) {
            throw new InvocationSubjectException(this, "Could not invoke method");
        } catch (InvocationTargetException ex) {
            final Throwable cause = ex.getCause();
            if (cause instanceof ConstraintStatusException statusException) {
                return statusException.getStatus();
            } else if (cause instanceof ConstraintReadinessException readinessException) {
                return readinessException.getReadiness().toStatus();
            } else {
                throw new InvocationRuntimeException(this, "Method unexpectedly threw exception at runtime", cause);
            }
        }

        if (result instanceof ConstraintStatus status) {
            return status;
        } else if (result == null) {
            return ConstraintStatus.PASSED;
        } else {
            throw new InvocationRuntimeException(this, "Expected ConstraintStatus or null as invocation result, instead got: " + result.getClass().getName());
        }
    }

    protected abstract Object _invoke(Object instance, Method method, Context context)
        throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;

}
