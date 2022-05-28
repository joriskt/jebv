package org.voidbucket.validator.constraint;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.voidbucket.validator.constraint.invoker.NoArgsMethodInvoker;
import org.voidbucket.validator.reflect.MethodInvoker;

import static org.junit.jupiter.api.Assertions.*;

class AlwaysPassConstraintTest {

    private static MethodInvoker INVOKER;

    @BeforeAll
    static void beforeAll() {
        INVOKER = new NoArgsMethodInvoker();
    }

    @Test
    public void evaluate_shouldReturnPassed() {
        // Arrange
        final Constraint constraint = new AlwaysPassConstraint();

        // Act
        final ConstraintStatus status = INVOKER.invoke(constraint, null);

        // Assert
        assertEquals(ConstraintStatus.PASSED, status);
    }

}
