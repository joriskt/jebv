package org.voidbucket.validator.reflect;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.voidbucket.validator.reflect.node.BasicNodeFactory;

import java.util.List;
import java.util.function.Predicate;

import static org.mockito.Mockito.*;

@Slf4j
class BasicTraverserTest {

    @Test
    public void traverse_whenOneField_shouldVisitAllOnce() {
        // Arrange
        final List<Predicate<Node>> visitPredicates = List.of();
        final List<Predicate<Node>> reportPredicates = List.of();
        final Traverser traverser = new BasicTraverser(new BasicNodeFactory(), visitPredicates, reportPredicates);

        final Visitor visitor = mock(Visitor.class);

        // Act
        traverser.traverse(ObjectWithField.class, visitor);

        // Assert
        verify(visitor, times(2)).visitType(any());
        verify(visitor, times(1)).visitField(any());
    }

    @Test
    public void traverse_whenOneMethod_shouldVisitMethodOnce() {
        // Arrange
        final List<Predicate<Node>> visitPredicates = List.of((node) -> node.getDepth() <= 1);
        final List<Predicate<Node>> reportPredicates = List.of((node) -> {
            if (node instanceof MethodNode) {
                return !((MethodNode) node).getMethod().getDeclaringClass().equals(Object.class);
            }
            return true;
        });
        final Traverser traverser = new BasicTraverser(new BasicNodeFactory(), visitPredicates, reportPredicates);

        final Visitor visitor = mock(Visitor.class);

        // Act
        traverser.traverse(ObjectWithMethod.class, visitor);

        // Assert
        verify(visitor, times(1)).visitMethod(any());
    }

    private static final class ObjectWithField {
        public int anInt;
    }

    private static final class ObjectWithMethod {
        public void method() {
        }
    }

}
