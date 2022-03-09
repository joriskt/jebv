package org.voidbucket.validator;

import org.voidbucket.validator.constraint.*;

public interface ValidatorState {

    ConstraintStatus getStatus(final Constraint constraint);
    ConstraintStatus getStatus(final ConstraintReference reference);

    ParameterHolder getParameterHolder();

}
