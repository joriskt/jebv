package org.voidbucket.validator.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface NodeFactory {
    TypeNode getTypeNode(final Node parent, final Class<?> type);
    FieldNode getFieldNode(final Node parent, final Field field);
    MethodNode getMethodNode(final Node parent, final Method method);
}
