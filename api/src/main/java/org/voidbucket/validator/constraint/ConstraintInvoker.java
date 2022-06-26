package org.voidbucket.validator.constraint;

import org.voidbucket.validator.Context;
import org.voidbucket.validator.reflect.invoke.MethodInvoker;

public interface ConstraintInvoker extends MethodInvoker {

    ConstraintStatus invokeConstraint(Constraint constraint, Context context);

}
