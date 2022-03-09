package org.voidbucket.validator.discover;

import org.junit.jupiter.api.Test;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintDiscoverer;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FixedListValidatorConstraintDiscovererTest {

    @Test
    public void discoverFromSubject_whenInputGiven_alwaysReturnsExactInput() {
        // Arrange
        final Set<Constraint> constraints = Set.of();
        final ConstraintDiscoverer discoverer = new FixedListValidatorConstraintDiscoverer(constraints);

        // Act
        final Set<? extends Constraint> output = discoverer.discoverFromSubject(null);

        // Assert
        assertTrue(output.isEmpty());
    }

    @Test
    public void discoverFromValidators_shouldReturnNull() {
        // Arrange
        final Set<Constraint> constraints = Set.of();
        final ConstraintDiscoverer discoverer = new FixedListValidatorConstraintDiscoverer(constraints);

        // Act
        final Set<? extends Constraint> output = discoverer.discoverFromValidators(null);

        // Assert
        assertSame(constraints, output);
    }

}
