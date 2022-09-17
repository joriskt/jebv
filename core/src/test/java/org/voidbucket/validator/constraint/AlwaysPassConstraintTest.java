package org.voidbucket.validator.constraint;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.voidbucket.validator.reflect.invoke.NoArgsConstraintInvoker;
import org.voidbucket.validator.reflect.invoke.ConstraintInvoker;

import static org.junit.jupiter.api.Assertions.*;

class AlwaysPassConstraintTest {

    private static ConstraintInvoker INVOKER;

    @BeforeAll
    static void beforeAll() {
        INVOKER = new NoArgsConstraintInvoker();
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
