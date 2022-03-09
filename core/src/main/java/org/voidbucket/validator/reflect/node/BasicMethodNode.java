package org.voidbucket.validator.reflect.node;

import org.voidbucket.validator.reflect.traverse.MethodNode;
import org.voidbucket.validator.reflect.traverse.Node;

import java.lang.reflect.Method;

public final class BasicMethodNode extends BaseNode implements MethodNode {

    private final Method method;

    BasicMethodNode(final Node parent,
                    final Method method) {
        super(parent, method.getName());
        this.method = method;
    }

    @Override
    public Method getMethod() {
        return method;
    }

}
