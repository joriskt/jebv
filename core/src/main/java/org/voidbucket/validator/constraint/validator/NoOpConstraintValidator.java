package org.voidbucket.validator.constraint.validator;

import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintStatus;
import org.voidbucket.validator.constraint.ConstraintValidator;
import org.voidbucket.validator.Context;

public final class NoOpConstraintValidator implements ConstraintValidator {

    @Override
    public ConstraintStatus validate(Context context, Constraint constraint) {
        return ConstraintStatus.PASSED;
    }

}
