package org.voidbucket.validator.reflect;

public interface Visitor {

    default void visitType(final TypeNode node) {
    }
    default void visitField(final FieldNode node) {
    }
    default void visitMethod(final MethodNode node) {
    }

}
