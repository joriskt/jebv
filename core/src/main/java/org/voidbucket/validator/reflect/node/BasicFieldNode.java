package org.voidbucket.validator.reflect.node;

import org.voidbucket.validator.reflect.traverse.FieldNode;
import org.voidbucket.validator.reflect.traverse.Node;

import java.lang.reflect.Field;

public class BasicFieldNode extends BaseNode implements FieldNode {

    private final Field field;

    BasicFieldNode(final Node parent,
                   final Field field) {
        super(parent, field.getName());
        this.field = field;
    }

    @Override
    public Field getField() {
        return field;
    }

}
