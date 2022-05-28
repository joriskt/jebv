package org.voidbucket.validator.impl;

import one.util.streamex.EntryStream;
import org.voidbucket.validator.ValidationReport;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintStatus;
import org.voidbucket.validator.violation.Violation;

import java.util.List;
import java.util.Map;

public class DefaultValidationReport implements ValidationReport {

    private final Map<Constraint, ConstraintStatus> statuses;
    private final List<Violation> violations;

    public DefaultValidationReport(final Map<Constraint, ConstraintStatus> statuses,
                                   final List<Violation> violations) {
        this.statuses = statuses;
        this.violations = violations;
    }

    @Override
    public Map<Constraint, ConstraintStatus> getStatuses() {
        return statuses;
    }

    @Override
    public List<Violation> getViolations() {
        return violations;
    }

    @Override
    public boolean isPassed() {
        return violations.isEmpty() && EntryStream.of(statuses)
            .values()
            .noneMatch(status -> status.isFailed() || status.isUnreachable());
    }

    @Override
    public boolean isFailed() {
        return !isPassed();
    }

}
