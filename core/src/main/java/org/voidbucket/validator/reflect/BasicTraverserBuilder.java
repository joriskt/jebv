package org.voidbucket.validator.reflect;

import lombok.Setter;
import lombok.experimental.Accessors;
import org.voidbucket.validator.reflect.node.BasicNodeFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Setter
@Accessors(fluent = true, chain = true)
public final class BasicTraverserBuilder {

    private Integer minDepth;
    private Integer maxDepth;
    private NodeFactory nodeFactory;

    private final List<Predicate<Node>> visitPredicates;
    private final List<Predicate<Node>> reportPredicates;

    BasicTraverserBuilder() {
        this.nodeFactory = new BasicNodeFactory();
        this.visitPredicates = new ArrayList<>();
        this.reportPredicates = new ArrayList<>();
    }

    public BasicTraverserBuilder visitWhen(final Predicate<Node> predicate) {
        visitPredicates.add(predicate);
        return this;
    }

    public BasicTraverserBuilder visitTypeWhen(final Predicate<TypeNode> predicate) {
        visitPredicates.add((node) -> !(node instanceof TypeNode) || predicate.test((TypeNode) node));
        return this;
    }
    public BasicTraverserBuilder visitFieldWhen(final Predicate<FieldNode> predicate) {
        visitPredicates.add((node) -> !(node instanceof FieldNode) || predicate.test((FieldNode) node));
        return this;
    }
    public BasicTraverserBuilder visitMethodWhen(final Predicate<MethodNode> predicate) {
        visitPredicates.add((node) -> !(node instanceof MethodNode) || predicate.test((MethodNode) node));
        return this;
    }

    public BasicTraverserBuilder reportWhen(final Predicate<Node> nodePredicate) {
        reportPredicates.add(nodePredicate);
        return this;
    }

    public Traverser build() {
        if (maxDepth != null) {
            visitPredicates.add((node) -> node.getDepth() <= maxDepth);
        }
        if (minDepth != null) {
            reportPredicates.add((node) -> node.getDepth() >= minDepth);
        }
        return new BasicTraverser(nodeFactory, visitPredicates, reportPredicates);
    }

}
