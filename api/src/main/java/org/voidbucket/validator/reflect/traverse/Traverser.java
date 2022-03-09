package org.voidbucket.validator.reflect.traverse;

public interface Traverser {

    void traverse(final Class<?> subject, final Visitor visitor);

}
