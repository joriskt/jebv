package org.voidbucket.validator;

import org.jetbrains.annotations.NotNull;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.violation.Violation;

/**
 * The {@link Context} is a construct that provides access to the objects and parameters
 * of a {@link ValidatorState}, or even those of a single {@link Constraint} validation pass.
 */
public interface Context {

    ValidatorState getState();
    Object getSubject();
    ParameterHolder getParams();

    void raise(@NotNull Violation violation);

}
