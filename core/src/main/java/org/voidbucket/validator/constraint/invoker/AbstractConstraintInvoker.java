package org.voidbucket.validator.constraint.invoker;

import org.voidbucket.validator.Context;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintInvoker;
import org.voidbucket.validator.constraint.ConstraintReadiness;
import org.voidbucket.validator.constraint.ConstraintStatus;
import org.voidbucket.validator.exception.ConstraintEvaluationException;
import org.voidbucket.validator.exception.ConstraintInvocationException;
import org.voidbucket.validator.exception.result.ConstraintReadinessException;
import org.voidbucket.validator.exception.result.ConstraintStatusException;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractConstraintInvoker implements ConstraintInvoker {

    @Override
    public final ConstraintStatus invoke(Constraint constraint, Context context) {
        try {
            final Object result = _invoke(constraint, context);
            if (result instanceof ConstraintStatus status) {
                return status;
            } else if (result instanceof ConstraintReadiness readiness) {
                return readiness.toStatus();
            } else {
                throw new ConstraintInvocationException(
                    constraint, this,
                    "Constraint result is neither a ConstraintStatus nor a ConstraintReadiness: " + result);
            }
        } catch (IllegalAccessException | InvocationTargetException ex) {
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
