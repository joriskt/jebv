package org.voidbucket.validator.reflect;

public interface Node {

    Path getPath();
    Node getParent();

    int getDepth();
    boolean isRootNode();

}
