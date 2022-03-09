package org.voidbucket.validator.constraint;

import org.voidbucket.validator.Context;

/**
 * Validates a {@link Constraint} using the given {@link Context},
 * returning a {@link ConstraintStatus}.
 */
public interface ConstraintValidator {

    ConstraintStatus validate(Context context, Constraint constraint);

}
