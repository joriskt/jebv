package org.voidbucket.validator.reflect.invoke;

import org.jetbrains.annotations.NotNull;
import org.voidbucket.validator.Context;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintStatus;

/**
 * A utility used to invoke methods using the given {@link Context}.
 */
public interface ConstraintInvoker extends Cloneable {

    @NotNull ConstraintStatus invoke(Constraint constraint, Context context)
        throws InvocationSubjectException, InvocationRuntimeException;

}
