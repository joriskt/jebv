package org.voidbucket.validator.reflect.traverse;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface NodeFactory {
    TypeNode createTypeNode(final Node parent, final Class<?> type);
    FieldNode createFieldNode(final Node parent, final Field field);
    MethodNode createMethodNode(final Node parent, final Method method);
}
