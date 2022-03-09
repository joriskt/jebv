package org.voidbucket.validator.constraint.middleware;

import org.voidbucket.validator.constraint.ConstraintMiddlewareChain;

public final class ConstraintMiddlewares {

    public static ConstraintMiddlewareChain chain() {
        return new ConstraintMiddlewareChainImpl();
    }

}
