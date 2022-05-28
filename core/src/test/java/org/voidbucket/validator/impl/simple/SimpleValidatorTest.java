package org.voidbucket.validator.impl.simple;

import org.junit.jupiter.api.Test;
import org.voidbucket.validator.ValidationReport;
import org.voidbucket.validator.Validator;
import org.voidbucket.validator.constraint.AlwaysFailConstraint;
import org.voidbucket.validator.constraint.AlwaysPassConstraint;
import org.voidbucket.validator.constraint.FixedViolationsConstraint;
import org.voidbucket.validator.discover.FixedListConstraintDiscoverer;
import org.voidbucket.validator.example.Empty;
import org.voidbucket.validator.violation.SimpleViolation;
import org.voidbucket.validator.violation.Violation;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleValidatorTest {

    @Test
    void validate_whenNoConstraints_shouldPass() {
        // Arrange
        final SimpleValidatorFactory factory = new SimpleValidatorFactory();
        factory.constraintDiscoverer(new FixedListConstraintDiscoverer());

        final Validator<Empty> validator = factory.createValidator(Empty.class);

        // Act
        final ValidationReport report = validator.validate(Empty.instance());

        // Assert
        assertNotNull(report);
        assertNotNull(report.getViolations());
        assertTrue(report.getViolations().isEmpty());
        assertTrue(report.isPassed());
        assertFalse(report.isFailed());
    }

    @Test
    void validate_whenSingleAlwaysPassConstraint_shouldPass() {
        // Arrange
        final SimpleValidatorFactory factory = new SimpleValidatorFactory();
        factory.constraintDiscoverer(new FixedListConstraintDiscoverer(
            new AlwaysPassConstraint()
        ));

        final Validator<Empty> validator = factory.createValidator(Empty.class);

        // Act
        final ValidationReport report = validator.validate(Empty.instance());

        // Assert
        assertNotNull(report);
        assertNotNull(report.getViolations());
        assertTrue(report.getViolations().isEmpty());
        assertTrue(report.isPassed());
        assertFalse(report.isFailed());
    }

    @Test
    void validate_whenSingleAlwaysFailConstraint_shouldFail() {
        // Arrange
        final SimpleValidatorFactory factory = new SimpleValidatorFactory();
        factory.constraintDiscoverer(new FixedListConstraintDiscoverer(
            new AlwaysFailConstraint()
        ));

        final Validator<Empty> validator = factory.createValidator(Empty.class);

        // Act
        final ValidationReport report = validator.validate(Empty.instance());

        // Assert
        assertNotNull(report);
        assertNotNull(report.getViolations());
        assertTrue(report.getViolations().isEmpty());
        assertFalse(report.isPassed());
        assertTrue(report.isFailed());
    }

    @Test
    void validate_whenSingleViolationConstraint_shouldFailWithViolation() {
        // Arrange
        final String templateString = "the.message.template.string";
        final SimpleValidatorFactory factory = new SimpleValidatorFactory();
        factory.constraintDiscoverer(new FixedListConstraintDiscoverer(
            new FixedViolationsConstraint(new SimpleViolation(templateString))
        ));

        final Validator<Empty> validator = factory.createValidator(Empty.class);

        // Act
        final ValidationReport report = validator.validate(Empty.instance());

        // Assert
        assertNotNull(report);
        assertFalse(report.isPassed());
        assertTrue(report.isFailed());

        final List<Violation> violations = report.getViolations();
        assertNotNull(violations);
        assertEquals(1, violations.size());

        final Violation violation = violations.get(0);
        assertNotNull(violation);
        assertEquals(templateString, violation.getMessageTemplate());
    }

}
