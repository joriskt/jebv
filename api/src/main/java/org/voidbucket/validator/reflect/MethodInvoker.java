package org.voidbucket.validator.reflect;

import org.voidbucket.validator.Context;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintStatus;
import org.voidbucket.validator.constraint.ConstraintValidator;

/**
 * A class that can invoke a {@link ConstraintValidator} using the given {@link Context}.
 */
public interface MethodInvoker extends Cloneable {

    ConstraintStatus invoke(Constraint constraint, Context context);

}
