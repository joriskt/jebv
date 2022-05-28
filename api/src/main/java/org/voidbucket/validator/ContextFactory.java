package org.voidbucket.validator;

import org.voidbucket.validator.constraint.Constraint;

public interface ContextFactory extends Cloneable {

    Context createValidationContext(ValidatorState state);
    Context createConstraintContext(Context parent, Constraint constraint);

}
