package org.voidbucket.validator.discover;

import org.junit.jupiter.api.Test;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintDiscoverer;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FixedListSubjectConstraintDiscovererTest {

    @Test
    public void discoverFromSubject_whenInputGiven_alwaysReturnsExactInput() {
        // Arrange
        final Set<Constraint> constraints = Set.of();
        final ConstraintDiscoverer discoverer = new FixedListSubjectConstraintDiscoverer(constraints);

        // Act
        final Set<? extends Constraint> output = discoverer.discoverFromSubject(null);

        // Assert
        assertSame(constraints, output);
    }

    @Test
    public void discoverFromValidators_shouldReturnNull() {
        // Arrange
        final Set<Constraint> constraints = Set.of();
        final ConstraintDiscoverer discoverer = new FixedListSubjectConstraintDiscoverer(constraints);

        // Act
        final Set<? extends Constraint> output = discoverer.discoverFromValidators(null);

        // Assert
        assertTrue(output.isEmpty());
    }

}
