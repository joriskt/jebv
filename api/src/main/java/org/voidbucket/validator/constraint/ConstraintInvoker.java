package org.voidbucket.validator.constraint;

import org.voidbucket.validator.Context;

/**
 * A class that can invoke a {@link ConstraintValidator} using the given {@link Context}.
 */
public interface ConstraintInvoker {

    ConstraintStatus invoke(Constraint constraint, Context context);

}
