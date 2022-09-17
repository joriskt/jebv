package org.voidbucket.validator.constraint;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.voidbucket.validator.reflect.invoke.NoArgsConstraintInvoker;
import org.voidbucket.validator.reflect.invoke.ConstraintInvoker;

import static org.junit.jupiter.api.Assertions.*;

class AlwaysFailConstraintTest {

    private static ConstraintInvoker INVOKER;

    @BeforeAll
    static void beforeAll() {
        INVOKER = new NoArgsConstraintInvoker();
    }

    @Test
    public void evaluate_shouldReturnFailed() {
        // Arrange
        final Constraint constraint = new AlwaysFailConstraint();

        // Act
        final ConstraintStatus status = INVOKER.invoke(constraint, null);

        // Assert
        assertEquals(ConstraintStatus.FAILED, status);
    }

}
