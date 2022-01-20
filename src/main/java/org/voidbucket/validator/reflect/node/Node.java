package org.voidbucket.validator.reflect.node;

import lombok.Getter;

import java.util.Set;

@Getter
public abstract class Node {

    private final Node parent;
    private final Path path;

    public Node(final Node parent,
                final String key) {
        this.parent = parent;
        this.path = parent != null ? parent.getPath().createChild(key) : new Path(key);
    }

    public final boolean isRootNode() {
        return path.isRoot();
    }

    public abstract Set<Node> getChildren();

    public abstract Object readValue(final Object subject);

    public abstract void writeValue(final Object subject, final Object value);

    public abstract NodeKind getKind();

    public abstract <T> T visit(final NodeVisitor<T> visitor);

    public abstract String toString();

    public static Node forType(final Class<?> type) {
        return new TypeNode(type);
    }

}
