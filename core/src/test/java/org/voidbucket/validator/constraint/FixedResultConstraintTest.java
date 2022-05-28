package org.voidbucket.validator.constraint;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.voidbucket.validator.constraint.invoker.NoArgsMethodInvoker;
import org.voidbucket.validator.reflect.MethodInvoker;

import static org.junit.jupiter.api.Assertions.*;

class FixedResultConstraintTest {

    private static MethodInvoker INVOKER;

    @BeforeAll
    static void beforeAll() {
        INVOKER = new NoArgsMethodInvoker();
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
