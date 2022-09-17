package org.voidbucket.validator.constraint;

import org.voidbucket.validator.Context;
import org.voidbucket.validator.Validator;

/**
 * <p>A {@link ConstraintValidator} is effectively a stand-alone subset of a {@link Validator}, aimed
 * at validating a specific set of {@link Constraint}s.</p>
 */
public interface ConstraintValidator {

    ConstraintStatus validate(Constraint constraint, Context context);

}
