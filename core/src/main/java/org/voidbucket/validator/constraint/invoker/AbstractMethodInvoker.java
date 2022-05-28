package org.voidbucket.validator.constraint.invoker;

import org.voidbucket.validator.Context;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.reflect.MethodInvoker;
import org.voidbucket.validator.constraint.Readiness;
import org.voidbucket.validator.constraint.ConstraintStatus;
import org.voidbucket.validator.exception.ConstraintEvaluationException;
import org.voidbucket.validator.exception.ConstraintInvocationException;
import org.voidbucket.validator.exception.result.ConstraintReadinessException;
import org.voidbucket.validator.exception.result.ConstraintStatusException;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractMethodInvoker implements MethodInvoker {

    protected ConstraintStatus getDefaultStatus() {
        return ConstraintStatus.PASSED;
    }

    @Override
    public final ConstraintStatus invoke(Constraint constraint, Context context) {
        final ConstraintStatus defaultStatus = getDefaultStatus();

        try {

            final Object result;
            try {
                 result = _invoke(constraint, context);
            } catch (InvocationTargetException ex) {
                throw ex.getCause();
            }

            if (result == null && defaultStatus != null) {
                return defaultStatus;
            } else if (result instanceof ConstraintStatus status) {
                return status;
            } else if (result instanceof Readiness readiness) {
                return readiness.toStatus();
            } else {
                throw new ConstraintInvocationException(
                    constraint, this,
                    "Constraint result is neither a ConstraintStatus nor a ConstraintReadiness: " + result);
            }
        } catch (IllegalAccessException ex) {
            throw new ConstraintInvocationException(constraint, this, "Failed to invoke constraint method", ex);
        } catch (ConstraintReadinessException ex) {
            return ex.getReadiness().toStatus();
        } catch (ConstraintStatusException ex) {
            return ex.getStatus();
        } catch (Throwable t) {
            throw new ConstraintEvaluationException(constraint, "Exception occurred during constraint evaluation", t);
        }
    }

    protected abstract Object _invoke(Constraint constraint, Context context) throws Throwable;

}
