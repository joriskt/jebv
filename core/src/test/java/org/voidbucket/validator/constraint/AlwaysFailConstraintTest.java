package org.voidbucket.validator.constraint;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.voidbucket.validator.reflect.invoke.NoArgsMethodInvoker;
import org.voidbucket.validator.reflect.invoke.MethodInvoker;

import static org.junit.jupiter.api.Assertions.*;

class AlwaysFailConstraintTest {

    private static MethodInvoker INVOKER;

    @BeforeAll
    static void beforeAll() {
        INVOKER = new NoArgsMethodInvoker();
    }

    @Test
    public void evaluate_shouldReturnFailed() {
        // Arrange
        final Constraint constraint = new AlwaysFailConstraint();

        // Act
        final ConstraintStatus status = INVOKER.invoke(, constraint, , null);

        // Assert
        assertEquals(ConstraintStatus.FAILED, status);
    }

}
