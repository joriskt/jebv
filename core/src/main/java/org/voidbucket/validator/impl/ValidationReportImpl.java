package org.voidbucket.validator.impl;

import org.voidbucket.validator.ValidationReport;
import org.voidbucket.validator.constraint.ConstraintValidator;
import org.voidbucket.validator.violation.Violation;

import java.util.List;

public class ValidationReportImpl implements ValidationReport {

    @Override
    public List<ConstraintValidator> getConstraints() {
        return null;
    }
    @Override
    public List<Violation> getViolations() {
        return null;
    }

}
