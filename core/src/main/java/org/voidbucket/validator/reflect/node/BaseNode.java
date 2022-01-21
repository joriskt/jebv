package org.voidbucket.validator.reflect.node;

import org.voidbucket.validator.reflect.Node;
import org.voidbucket.validator.reflect.Path;

public abstract class BaseNode implements Node {

    private final Node parent;
    private final Path path;

    BaseNode(final Node parent, final String key) {
        this.parent = parent;
        this.path = parent == null ? new Path(key) : parent.getPath().createChild(key);
    }

    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public int getDepth() {
        return isRootNode() ? 0 : (parent.getDepth() + 1);
    }

    @Override
    public boolean isRootNode() {
        return parent == null;
    }


}
