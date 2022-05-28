package org.voidbucket.validator;

import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintStatus;
import org.voidbucket.validator.violation.Violation;

import java.util.List;
import java.util.Map;

public interface ValidationReport {

    Map<Constraint, ConstraintStatus> getStatuses();
    List<Violation> getViolations();

    boolean isPassed();
    boolean isFailed();

}
