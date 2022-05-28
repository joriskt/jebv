package org.voidbucket.validator.constraint;

import org.jetbrains.annotations.NotNull;

/**
 * A {@link Constraint} implementation that always results in {@link ConstraintStatus#PASSED}.
 */
public class AlwaysPassConstraint extends FixedResultConstraint {

    public AlwaysPassConstraint() {
        super(ConstraintStatus.PASSED);
    }

    public AlwaysPassConstraint(final @NotNull ReadinessEvaluator middlewareChain) {
        super(ConstraintStatus.PASSED, middlewareChain);
    }

}
