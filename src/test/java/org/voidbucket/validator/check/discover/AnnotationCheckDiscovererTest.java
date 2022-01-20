package org.voidbucket.validator.check.discover;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.voidbucket.validator.annotation.Checks;
import org.voidbucket.validator.check.CheckDefinition;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class AnnotationCheckDiscovererTest {

    public static class CheckClass {
    }

    public static class UncheckedClass {}

    @Checks(CheckClass.class)
    public static class CheckedClass {
    }

    public static class UncheckedClassWithUncheckedChildType {
        private UncheckedClass child;
    }

    public static class UncheckedClassWithUncheckedChildField {
        private String child;
    }

    @Checks(CheckClass.class)
    public static class CheckedClassWithUncheckedChildType {
        private UncheckedClass child;
    }

    public static class UncheckedClassWithCheckedChildType {
        private CheckedClass child;
    }

    public static class UncheckedClassWithCheckedChildField {
        @Checks(CheckClass.class)
        private UncheckedClass child;
    }

    private static AnnotationCheckDiscoverer annotationCheckDiscoverer;

    @BeforeAll
    static void beforeEach() {
        annotationCheckDiscoverer = new AnnotationCheckDiscoverer();
    }

    @Test
    public void discover_whenUncheckedClass_findsNone() {
        // Arrange
        final Class<?> subject = UncheckedClass.class;

        // Act
        final Set<CheckDefinition> definitions = annotationCheckDiscoverer.discover(subject);

        // Assert
        assertTrue(definitions.isEmpty());
    }

    @Test
    public void discover_whenCheckedClass_findsAll() {
        // Arrange
        final Class<?> subject = CheckedClass.class;

        // Act
        final Set<CheckDefinition> definitions = annotationCheckDiscoverer.discover(subject);

        // Assert
        assertEquals(1, definitions.size());

        final CheckDefinition definition = definitions.stream().findFirst().orElseThrow();
        assertEquals("{CheckedClass}", definition.getNode().getPath().toString());
    }

    @Test
    public void discover_whenUncheckedClassWithUncheckedChildType_findsNone() {
        // Arrange
        final Class<?> subject = UncheckedClassWithUncheckedChildType.class;

        // Act
        final Set<CheckDefinition> definitions = annotationCheckDiscoverer.discover(subject);

        // Assert
        assertTrue(definitions.isEmpty());
    }

    @Test
    public void discover_whenUncheckedClassWithUncheckedChildField_findsNone() {
        // Arrange
        final Class<?> subject = UncheckedClassWithUncheckedChildField.class;

        // Act
        final Set<CheckDefinition> definitions = annotationCheckDiscoverer.discover(subject);

        // Assert
        assertTrue(definitions.isEmpty());
    }

    @Test
    public void discover_whenCheckedClassWithUncheckedChild_findsOne() {
        // Arrange
        final Class<?> subject = CheckedClassWithUncheckedChildType.class;

        // Act
        final Set<CheckDefinition> definitions = annotationCheckDiscoverer.discover(subject);

        // Assert
        assertEquals(1, definitions.size());
    }

    @Test
    public void discover_whenUncheckedClassWithCheckedChildType_findsOne() {
        // Arrange
        final Class<?> subject = UncheckedClassWithCheckedChildType.class;

        // Act
        final Set<CheckDefinition> definitions = annotationCheckDiscoverer.discover(subject);

        // Assert
        assertEquals(1, definitions.size());
    }

    @Test
    public void discover_whenUncheckedClassWithCheckedChildField_findsOne() {
        // Arrange
        final Class<?> subject = UncheckedClassWithCheckedChildField.class;

        // Act
        final Set<CheckDefinition> definitions = annotationCheckDiscoverer.discover(subject);

        // Assert
        assertEquals(1, definitions.size());
        log.info(definitions.stream().findFirst().orElseThrow().getNode().getPath().toString());
    }

}
