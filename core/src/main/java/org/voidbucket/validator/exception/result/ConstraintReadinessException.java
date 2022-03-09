package org.voidbucket.validator.exception.result;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.voidbucket.validator.constraint.ConstraintReadiness;

@RequiredArgsConstructor
public class ConstraintReadinessException extends RuntimeException {

    @Getter
    public final ConstraintReadiness readiness;

}
