package org.voidbucket.validator.reflect.node;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FieldNodeTest {

    static class RootClass {
        private RootClass child;
    }

    @Test
    public void construct_whenHasParent_shouldCreateChildPath() throws NoSuchFieldException {
        // Arrange
        final TypeNode typeNode = new TypeNode(RootClass.class);
        final FieldNode fieldNode = new FieldNode(typeNode, RootClass.class.getDeclaredField("child"));

        // Act
        final Path path = fieldNode.getPath();

        // Arrange
        assertEquals("{RootClass}.child", path.toString());
    }

    @Test
    public void getChildren_shouldReturnChildTypeNode() throws NoSuchFieldException {
        // Arrange
        final TypeNode typeNode = new TypeNode(RootClass.class);
        final FieldNode fieldNode = new FieldNode(typeNode, RootClass.class.getDeclaredField("child"));

        // Act
        final Set<Node> childNodes = fieldNode.getChildren();

        // Assert
        assertEquals(1, childNodes.size());
        final Node node = childNodes.stream().findFirst().orElseThrow();
        assertEquals(NodeKind.TYPE, node.getKind());
        assertEquals("{RootClass}.child.{RootClass}", node.getPath().toString());
    }

}
