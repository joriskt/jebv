package org.voidbucket.validator.reflect;

public interface Traverser {

    void traverse(final Class<?> subject, final Visitor visitor);

}
