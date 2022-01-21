package org.voidbucket.validator.check.provider;

import org.junit.jupiter.api.Test;
import org.voidbucket.validator.ValidatorContext;
import org.voidbucket.validator.check.Check;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StaticCheckProviderTest {

    @Test
    public void register_whenNull_shouldRaiseException() {
        // Arrange
        final StaticCheckProvider provider = new StaticCheckProvider();

        // Act
        assertThrows(NullPointerException.class, () -> provider.register(null));
    }

    @Test
    public void provide_whenNotRegistered_shouldRaiseException() {
        // Arrange
        final StaticCheckProvider provider = new StaticCheckProvider();

        // Act
        assertThrows(IllegalArgumentException.class, () -> provider.provide(String.class));
    }

    @Test
    public void provide_whenRegistered_shouldReturnInstance() {
        // Arrange
        final StaticCheckProvider provider = new StaticCheckProvider();
        final Check check1 = new NoOpCheck();
        provider.register(check1);

        // Act
        final Check check2 = provider.provide(NoOpCheck.class);

        // Assert
        assertSame(check1, check2);
    }

    private static final class NoOpCheck implements Check {
        @Override
        public boolean canRun(ValidatorContext context) {
            throw new UnsupportedOperationException();
        }
        @Override
        public boolean shouldRun(ValidatorContext context) {
            throw new UnsupportedOperationException();
        }
        @Override
        public void check(ValidatorContext context) {
            throw new UnsupportedOperationException();
        }

    }

}
