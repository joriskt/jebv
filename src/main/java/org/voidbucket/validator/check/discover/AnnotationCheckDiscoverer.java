package org.voidbucket.validator.check.discover;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.voidbucket.validator.annotation.Check;
import org.voidbucket.validator.annotation.Checks;
import org.voidbucket.validator.check.CheckDefinition;
import org.voidbucket.validator.reflect.node.FieldNode;
import org.voidbucket.validator.reflect.node.Node;
import org.voidbucket.validator.reflect.node.NodeVisitor;
import org.voidbucket.validator.reflect.node.TypeNode;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class AnnotationCheckDiscoverer implements CheckDiscoverer {

    private final Visitor visitor;

    public AnnotationCheckDiscoverer() {
        visitor = new Visitor();
    }

    private Set<CheckDefinition> extractMethods(final Set<CheckReference> references) {
        return references.stream()
            .map(checkReference -> {
                final List<Method> checkMethods = Arrays.stream(checkReference.getType().getMethods())
                    .filter(method -> method.isAnnotationPresent(Check.class))
                    .toList();

                if (checkMethods.isEmpty()) {
                    log.warn("Detected CheckReference without any methods annotated with @Check: {}",
                        checkReference.getType().getCanonicalName());
                    return null;
                }

                return new CheckDefinition(checkReference.getNode(), checkReference.getType(), checkMethods);
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
    }

    @Override
    public Set<CheckDefinition> discover(final Class<?> type) {
        return extractMethods(Node.forType(type).visit(visitor));
    }

    @Slf4j
    private static class Visitor implements NodeVisitor<Set<CheckReference>> {

        private Set<CheckReference> extractSelf(final Node node, final Checks annotation) {
            if (annotation == null) {
                return new HashSet<>();
            }

            return Arrays.stream(annotation.value())
                .map(type -> new CheckReference(node, type))
                .collect(Collectors.toSet());
        }

        private Set<CheckReference> extractChildren(final Set<CheckReference> own, final Node node) {
            for (Node child : node.getChildren()) {
                own.addAll(child.visit(this));
            }
            return own;
        }

        @Override
        public Set<CheckReference> visitType(final TypeNode node) {
            final Class<?> type = node.getType();
            log.debug("Visiting path {} with type {}", node.getPath(), type.getCanonicalName());

            final Checks checks = type.getAnnotation(Checks.class);
            if (checks == null && !node.isRootNode()) {
                // Do not investigate a Class if the Class itself does not have a @Checks annotation.
                return new HashSet<>();
            }

            final Set<CheckReference> defs = extractSelf(node, checks);
            if (node.isRootNode()) {
                extractChildren(defs, node);
            }
            return defs;
        }

        @Override
        public Set<CheckReference> visitField(FieldNode node) {
            final Field field = node.getField();
            log.debug("Visiting path {} of type {}", node.getPath(), field.getType());

            final Set<CheckReference> defs = extractSelf(node, field.getAnnotation(Checks.class));
            extractChildren(defs, node);
            return defs;
        }

    }

    @Getter
    @RequiredArgsConstructor
    public static final class CheckReference {
        private final Node node;
        private final Class<?> type;
    }

}
