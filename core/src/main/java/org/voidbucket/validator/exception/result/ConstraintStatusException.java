package org.voidbucket.validator.exception.result;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.voidbucket.validator.constraint.ConstraintStatus;

@RequiredArgsConstructor
public class ConstraintStatusException extends RuntimeException {

    @Getter
    public final ConstraintStatus status;

}
