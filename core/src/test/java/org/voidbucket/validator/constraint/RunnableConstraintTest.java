package org.voidbucket.validator.constraint;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.voidbucket.validator.exception.result.ConstraintStatusException;
import org.voidbucket.validator.reflect.invoke.ConstraintInvoker;
import org.voidbucket.validator.reflect.invoke.NoArgsConstraintInvoker;

import static org.junit.jupiter.api.Assertions.*;

class RunnableConstraintTest {

    private static ConstraintInvoker INVOKER;

    @BeforeAll
    static void beforeAll() {
        INVOKER = new NoArgsConstraintInvoker();
    }

    @Test
    public void evaluate_whenThrowingStatus_shouldReturnStatus() {
        for (ConstraintStatus expected : ConstraintStatus.values()) {

            // Arrange
            final Constraint constraint = new RunnableConstraint(() -> {
                throw new ConstraintStatusException(expected);
            });

            // Act
            final ConstraintStatus status = INVOKER.invoke(constraint, null);

            // Assert
            assertEquals(expected, status);
        }
    }

}
