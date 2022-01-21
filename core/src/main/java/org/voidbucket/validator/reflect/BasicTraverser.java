package org.voidbucket.validator.reflect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Predicate;

@Slf4j
@RequiredArgsConstructor
public final class BasicTraverser implements Traverser {

    private final NodeFactory nodeFactory;
    private final List<Predicate<Node>> visitPredicates;
    private final List<Predicate<Node>> reportPredicates;

    private boolean shouldSkip(final Node node) {
        return !visitPredicates.stream().allMatch(pred -> pred.test(node));
    }
    private boolean shouldReport(final Node node) {
        return reportPredicates.stream().allMatch(pred -> pred.test(node));
    }

    @Override
    public void traverse(final Class<?> subject, final Visitor visitor) {
        final TypeNode root = nodeFactory.getTypeNode(null, subject);
        visitType(root, visitor);
    }

    private void visitType(final TypeNode node, final Visitor visitor) {
        if (shouldSkip(node)) {
            return;
        }
        if (shouldReport(node)) {
            visitor.visitType(node);
        }

        // Traverse fields.
        final Field[] fields = node.getType().getFields();
        for (Field field : fields) {
            final FieldNode fieldNode = nodeFactory.getFieldNode(node, field);
            visitField(fieldNode, visitor);
        }


        // Traverse methods.
        final Method[] methods = node.getType().getMethods();
        for (Method method : methods) {
            final MethodNode methodNode = nodeFactory.getMethodNode(node, method);
            visitMethod(methodNode, visitor);
        }
    }

    private void visitField(final FieldNode node, final Visitor visitor) {
        if (shouldSkip(node)) {
            return;
        }
        if (shouldReport(node)) {
            visitor.visitField(node);
        }

        // Traverse the type of this field.
        final Class<?> type = node.getField().getType();
        final TypeNode typeNode = nodeFactory.getTypeNode(node, type);
        visitType(typeNode, visitor);
    }

    private void visitMethod(final MethodNode node, final Visitor visitor) {
        if (shouldSkip(node)) {
            return;
        }
        if (shouldReport(node)) {
            visitor.visitMethod(node);
        }
        // Method nodes do not recurse.
    }

    public static BasicTraverserBuilder builder() {
        return new BasicTraverserBuilder();
    }

}
