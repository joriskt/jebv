package org.voidbucket.validator.reflect.node;

import org.voidbucket.validator.reflect.Node;
import org.voidbucket.validator.reflect.TypeNode;

public final class BasicTypeNode extends BaseNode implements TypeNode {

    private final Class<?> type;

    BasicTypeNode(final Node parent,
                  final Class<?> type) {
        super(parent, type.getSimpleName());
        this.type = type;
    }

    @Override
    public Class<?> getType() {
        return type;
    }

}
