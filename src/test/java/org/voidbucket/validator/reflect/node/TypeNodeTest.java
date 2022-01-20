package org.voidbucket.validator.reflect.node;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeNodeTest {

    @Test
    public void construct_whenNoParent_shouldCreateEmptyRootPath() {
        // Arrange
        final TypeNode node = new TypeNode(TypeNode.class);

        // Act
        final Path path = node.getPath();

        // Assert
        assertEquals("(TypeNode)", path.toString());
    }

}
