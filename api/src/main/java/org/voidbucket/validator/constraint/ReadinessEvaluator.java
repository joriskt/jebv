package org.voidbucket.validator.constraint;

import org.jetbrains.annotations.NotNull;
import org.voidbucket.validator.ValidatorState;

/**
 * Evaluates the <b>readiness</b> of this {@link Constraint}. Useful to intercept attempts to evaluate
 * a {@link Constraint} before its dependencies are met.
 */
public interface ReadinessEvaluator extends Comparable<ReadinessEvaluator> {

    /**
     * Evaluate the readiness of the {@link Constraint} that this {@link ReadinessEvaluator} represents.
     */
    Readiness evaluate(ValidatorState state);

    /**
     * Determines the priority of this {@link ReadinessEvaluator}. A higher number means earlier in the chain.
     * @return the priority of this {@link ReadinessEvaluator}
     */
    default int priority() {
        return 100_000;
    }

    @Override
    default int compareTo(@NotNull ReadinessEvaluator other) {
        return Integer.compare(this.priority(), other.priority());
    }

}
