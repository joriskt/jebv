package org.voidbucket.validator.constraint.validator;

import org.voidbucket.validator.Context;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintStatus;
import org.voidbucket.validator.constraint.ConstraintValidator;

public final class FixedResultConstraintValidator implements ConstraintValidator {

    private final ConstraintStatus status;

    public FixedResultConstraintValidator(final ConstraintStatus status) {
        this.status = status;
    }

    @Override
    public ConstraintStatus validate(final Context context, final Constraint constraint) {
        return status;
    }

}
