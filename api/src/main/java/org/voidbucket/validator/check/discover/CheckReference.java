package org.voidbucket.validator.check.discover;

import org.voidbucket.validator.reflect.Node;

/**
 * A reference from a Node to a class that contains Checks.
 */
public interface CheckReference {
    Node getNode();
    Class<?> getType();
}
