package org.voidbucket.validator.discover;

import org.junit.jupiter.api.Test;
import org.voidbucket.validator.ValidatorContext;
import org.voidbucket.validator.annotation.UseChecks;
import org.voidbucket.validator.check.Check;
import org.voidbucket.validator.check.discover.AnnotationCheckDiscoverer;
import org.voidbucket.validator.check.discover.CheckDiscoverer;
import org.voidbucket.validator.check.discover.CheckReference;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnnotationCheckDiscovererTest {

    @Test
    public void discover_whenNoAnnotation_shouldReturnEmptyList() {
        // Arrange
        final CheckDiscoverer discoverer = new AnnotationCheckDiscoverer();

        // Act
        final List<CheckReference> references = discoverer.discover(NoAnnotation.class);

        // Assert
        assertTrue(references.isEmpty());
    }

    @Test
    public void discover_whenRootAnnotation_shouldReturnOne() {
        // Arrange
        final CheckDiscoverer discoverer = new AnnotationCheckDiscoverer();

        // Act
        final List<CheckReference> references = discoverer.discover(RootAnnotation.class);

        // Assert
        assertEquals(1, references.size());

        final CheckReference reference = references.get(0);
        assertEquals(NoOpCheck.class, reference.getType());
    }

    @Test
    public void discover_whenFieldAnnotation_shouldReturnOne() {
        // Arrange
        final CheckDiscoverer discoverer = new AnnotationCheckDiscoverer();

        // Act
        final List<CheckReference> references = discoverer.discover(FieldAnnotation.class);

        // Assert
        assertEquals(1, references.size());

        final CheckReference reference = references.get(0);
        assertEquals(NoOpCheck.class, reference.getType());
    }

    @Test
    public void discover_whenChildWithAnnotation_shouldReturnOne() {
        // Arrange
        final CheckDiscoverer discoverer = new AnnotationCheckDiscoverer();

        // Act
        final List<CheckReference> references = discoverer.discover(ChildWithAnnotation.class);

        // Assert
        assertEquals(1, references.size());

        final CheckReference reference = references.get(0);
        assertEquals(NoOpCheck.class, reference.getType());
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

    private static final class NoAnnotation {
    }

    @UseChecks({ NoOpCheck.class }) // <-- this is a CheckerReference. It references the NoAnnotation Checker class.
    private static final class RootAnnotation {
    }

    private static final class FieldAnnotation {
        @UseChecks({ NoOpCheck.class })
        public int field;
    }

    private static final class ChildWithAnnotation {
        public RootAnnotation child;
    }

}
