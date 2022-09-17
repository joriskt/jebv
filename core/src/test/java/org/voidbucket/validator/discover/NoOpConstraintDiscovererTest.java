package org.voidbucket.validator.discover;

import org.junit.jupiter.api.Test;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintDiscoverer;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NoOpConstraintDiscovererTest {

    @Test
    public void discover_whenInvoked_shouldReturnEmptySet() {
        // Arrange
        final ConstraintDiscoverer discoverer = new NoOpConstraintDiscoverer();

        // Act
        final Set<? extends Constraint> constraintSet = discoverer.discover(getClass());

        // Assert
        assertNotNull(constraintSet);
        assertTrue(constraintSet.isEmpty());
    }

}
