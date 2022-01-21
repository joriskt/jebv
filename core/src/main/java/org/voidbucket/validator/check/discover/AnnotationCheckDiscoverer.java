package org.voidbucket.validator.check.discover;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.voidbucket.validator.annotation.UseChecks;
import org.voidbucket.validator.reflect.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AnnotationCheckDiscoverer implements CheckDiscoverer {

    /**
     * Traverses the subject recursively to find @UseChecks annotations.
     */
    private final Traverser subjectTraverser;

    public AnnotationCheckDiscoverer() {
        subjectTraverser = BasicTraverser.builder()
            .maxDepth(4)
            .visitTypeWhen(typeNode -> typeNode.getDepth() == 0 || typeNode.getType().isAnnotationPresent(UseChecks.class))
            .build();
    }

    @Override
    public List<CheckReference> discover(final Class<?> subjectType) {

        // Traverse the subject type to detect all classes annotated with @UseChecks.
        final SubjectVisitor subjectVisitor = new SubjectVisitor();
        subjectTraverser.traverse(subjectType, subjectVisitor);

        return subjectVisitor.getReferences();
    }

    private static final class SubjectVisitor implements Visitor {

        @Getter
        private final List<CheckReference> references;

        SubjectVisitor() {
            references = new ArrayList<>();
        }

        @Override
        public void visitType(final TypeNode node) {
            if (!node.getType().isAnnotationPresent(UseChecks.class)) {
                return;
            }

            final UseChecks useChecks = node.getType().getAnnotation(UseChecks.class);
            for (Class<?> type : useChecks.value()) {
                references.add(new AnnotationCheckReference(node, useChecks, type));
            }
        }

        @Override
        public void visitField(final FieldNode node) {
            if (!node.getField().isAnnotationPresent(UseChecks.class)) {
                return;
            }

            final UseChecks useChecks = node.getField().getAnnotation(UseChecks.class);
            for (Class<?> type : useChecks.value()) {
                references.add(new AnnotationCheckReference(node, useChecks, type));
            }
        }

        @Override
        public void visitMethod(final MethodNode node) {
        }

    }

}
