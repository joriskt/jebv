package org.voidbucket.validator.reflect;

import org.junit.jupiter.api.Test;
import org.voidbucket.validator.reflect.traverse.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PathTest {

    @Test
    void toString_whenUnnamedRoot_shouldReturnEmpty() {
        // Arrange
        final Path path = new Path();

        // Act
        final String str = path.toString();

        // Assert
        assertEquals("", str);
    }

    @Test
    void toString_whenNamedRoot_shouldReturnName() {
        // Arrange
        final Path path = new Path("root");

        // Act
        final String str = path.toString();

        // Assert
        assertEquals("root", str);
    }

    @Test
    void toString_whenUnnamedRootWithChild_shouldReturnPeriodAndChildKey() {
        // Arrange
        final Path root = new Path();
        final Path field = root.createChild("field");

        // Act
        final String str = field.toString();

        // Assert
        assertEquals(".field", str);
    }

    @Test
    void toString_whenNamedRootWithChild_shouldReturnRootKeyPeriodChildKey() {
        // Arrange
        final Path root = new Path("root");
        final Path field = root.createChild("field");

        // Act
        final String str = field.toString();

        // Assert
        assertEquals("root.field", str);
    }

}
