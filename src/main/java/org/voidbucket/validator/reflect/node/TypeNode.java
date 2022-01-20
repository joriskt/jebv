package org.voidbucket.validator.reflect.node;

import lombok.Getter;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.*;

import static org.voidbucket.validator.reflect.node.NodeKind.TYPE;

public class TypeNode extends Node {

    @Getter
    private final Class<?> type;

    TypeNode(final Class<?> type) {
        this(null, type);
    }

    TypeNode(final Node parent,
             final Class<?> type) {
        super(parent, String.format("{%s}", type.getSimpleName()));
        this.type = type;
    }

    @Override
    public Set<Node> getChildren() {
        final Set<Node> children = new HashSet<>();
        for (final Field field : type.getDeclaredFields()) {
            children.add(new FieldNode(this, field));
        }
        return children;
    }

    @Override
    @SneakyThrows
    public Object readValue(final Object subject) {
        if (!subject.getClass().equals(type)) {
            throw new IllegalAccessException("");
        }
        return subject;
    }

    @Override
    public void writeValue(final Object subject, final Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NodeKind getKind() {
        return TYPE;
    }

    @Override
    public <T> T visit(final NodeVisitor<T> visitor) {
        return visitor.visitType(this);
    }

    @Override
    public String toString() {
        return String.format("TypeNode(%s)", getPath().toString());
    }

}
