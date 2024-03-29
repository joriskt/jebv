package org.voidbucket.validator.constraint;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.voidbucket.validator.reflect.invoke.NoArgsConstraintInvoker;
import org.voidbucket.validator.reflect.invoke.ConstraintInvoker;

import static org.junit.jupiter.api.Assertions.*;

class FixedResultConstraintTest {

    private static ConstraintInvoker INVOKER;

    @BeforeAll
    static void beforeAll() {
        INVOKER = new NoArgsConstraintInvoker();
    }

    @Test
    public void evaluate_whenGivenStatus_shouldReturnStatus() {
        for (ConstraintStatus expected : ConstraintStatus.values()) {
            // Arrange
            final Constraint constraint = new FixedResultConstraint(expected);

            // Act
            final ConstraintStatus status = INVOKER.invoke(constraint, null);

            // Assert
            assertEquals(expected, status);
        }
    }

}
