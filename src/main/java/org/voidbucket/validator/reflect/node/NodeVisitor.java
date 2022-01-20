package org.voidbucket.validator.reflect.node;

public interface NodeVisitor<T> {
    T visitType(final TypeNode node);
    T visitField(final FieldNode node);
}
