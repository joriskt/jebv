package org.voidbucket.validator.violation;

import org.voidbucket.validator.constraint.ConstraintValidator;

import java.util.Map;

public interface Violation {
    ConstraintValidator getConstraint();
    String getMessageTemplate();
    Map<String, Object> getMessageParameters();
}
