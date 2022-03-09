package org.voidbucket.validator;

import org.voidbucket.validator.constraint.ConstraintValidator;
import org.voidbucket.validator.violation.Violation;

import java.util.List;

public interface ValidationReport {
    List<ConstraintValidator> getConstraints();
    List<Violation> getViolations();
}
