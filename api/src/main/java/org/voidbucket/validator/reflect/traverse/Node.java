package org.voidbucket.validator.reflect.traverse;

public interface Node {

    Path getPath();
    Node getParent();

    int getDepth();
    boolean isRootNode();

}
