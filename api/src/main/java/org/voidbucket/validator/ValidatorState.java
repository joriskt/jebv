package org.voidbucket.validator;

import org.jetbrains.annotations.NotNull;
import org.voidbucket.validator.constraint.*;
import org.voidbucket.validator.violation.Violation;

public interface ValidatorState {

    Object getSubject();

    ConstraintStatus getStatus(@NotNull Constraint constraint);
    ConstraintStatus getStatus(@NotNull ConstraintReference reference);

    Context getContext();
    void raise(@NotNull Violation violation);

}
