package org.voidbucket.validator.reflect.node;

import lombok.Getter;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Set;

@Getter
public class FieldNode extends Node {

    private final Field field;

    public FieldNode(final Node parent, final Field field) {
        super(parent, field.getName());
        this.field = field;
    }

    @Override
    public Set<Node> getChildren() {
        return Set.of(new TypeNode(this, field.getType()));
    }

    @Override
    @SneakyThrows
    public Object readValue(final Object subject) {
        field.setAccessible(true);
        return field.get(subject);
    }

    @Override
    @SneakyThrows
    public void writeValue(final Object subject, final Object value) {
        field.setAccessible(true);
        field.set(subject, value);
    }

    @Override
    public NodeKind getKind() {
        return NodeKind.FIELD;
    }

    @Override
    public <T> T visit(final NodeVisitor<T> visitor) {
        return visitor.visitField(this);
    }

    @Override
    public String toString() {
        return String.format("FieldNode(%s)", getPath().toString());
    }

}
