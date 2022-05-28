package org.voidbucket.validator;

import org.jetbrains.annotations.NotNull;
import org.voidbucket.validator.violation.Violation;

public interface Context {

    ValidatorState getState();
    Object getSubject();
    ParameterHolder getParams();

    void raise(@NotNull Violation violation);

}
