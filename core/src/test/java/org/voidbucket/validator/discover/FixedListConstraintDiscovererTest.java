package org.voidbucket.validator.discover;

import org.junit.jupiter.api.Test;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintDiscoverer;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FixedListConstraintDiscovererTest {

    @Test
    public void discoverFromSubject_whenInputGiven_alwaysReturnsExactInput() {
        // Arrange
        final Set<Constraint> constraints = Set.of();
        final ConstraintDiscoverer discoverer = new FixedListConstraintDiscoverer(constraints);

        // Act
        final Set<? extends Constraint> output = discoverer.discover(null);

        // Assert
        assertSame(constraints, output);
    }

    @Test
    public void discoverFromValidators_shouldReturnNull() {
        // Arrange
        final Set<Constraint> constraints = Set.of();
        final ConstraintDiscoverer discoverer = new FixedListConstraintDiscoverer(constraints);

        // Act
        final Set<? extends Constraint> output = discoverer.discover(null);

        // Assert
        assertTrue(output.isEmpty());
    }

}
