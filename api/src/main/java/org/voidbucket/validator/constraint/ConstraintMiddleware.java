package org.voidbucket.validator.constraint;

import org.jetbrains.annotations.NotNull;
import org.voidbucket.validator.ValidatorState;

/**
 * Evaluates the <b>readiness</b> of this {@link Constraint}. Useful to intercept attempts to evaluate
 * a {@link Constraint} before its dependencies are met.
 */
public interface ConstraintMiddleware extends Comparable<ConstraintMiddleware> {

    /**
     * Evaluate the readiness of the {@link Constraint} that this {@link ConstraintMiddleware} represents.
     */
    ConstraintReadiness evaluate(ValidatorState state);

    /**
     * Determines the priority of this {@link ConstraintMiddleware}. A higher number means earlier in the chain.
     * @return the priority of this {@link ConstraintMiddleware}
     */
    default int priority() {
        return 100_000;
    }

    @Override
    default int compareTo(@NotNull ConstraintMiddleware other) {
        return Integer.compare(this.priority(), other.priority());
    }

}
