package org.voidbucket.validator.reflect.node;

import org.voidbucket.validator.reflect.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

public final class BasicNodeFactory implements NodeFactory {

    @Override
    public TypeNode getTypeNode(Node parent, Class<?> type) {
        Objects.requireNonNull(type);
        return new BasicTypeNode(parent, type);
    }

    @Override
    public FieldNode getFieldNode(Node parent, Field field) {
        Objects.requireNonNull(parent);
        Objects.requireNonNull(field);
        return new BasicFieldNode(parent, field);
    }

    @Override
    public MethodNode getMethodNode(Node parent, Method method) {
        Objects.requireNonNull(parent);
        Objects.requireNonNull(method);
        return new BasicMethodNode(parent, method);
    }

}
