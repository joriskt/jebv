package org.voidbucket.validator.reflect.traverse;

import java.lang.reflect.Field;

public interface FieldNode extends Node {
    Field getField();
}
