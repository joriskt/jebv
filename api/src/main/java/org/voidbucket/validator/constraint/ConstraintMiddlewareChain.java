package org.voidbucket.validator.constraint;

import java.util.function.Supplier;

/**
 * Evaluates the <b>readiness</b> of this {@link Constraint}. Useful to intercept attempts to evaluate
 * a {@link Constraint} before its dependencies are met.
 */
public interface ConstraintMiddlewareChain extends ConstraintMiddleware {

    void register(ConstraintMiddleware evaluator);
    boolean unregister(Class<? extends ConstraintMiddleware> middlewareType);

    <T extends ConstraintMiddleware> T find(Class<T> middlewareType);
    <T extends ConstraintMiddleware> T findOrRegister(Class<T> middlewareType, Supplier<T> supplier);

    /**
     * Checks if this chain contains the given {@link ConstraintMiddleware} type, using
     * {@link Class#isAssignableFrom(Class)} as the comparison prediate.
     * @param middlewareType the type of the middleware
     * @return whether this chain contains the given type either exactly or through inheritance
     */
    boolean contains(Class<? extends ConstraintMiddleware> middlewareType);

}
