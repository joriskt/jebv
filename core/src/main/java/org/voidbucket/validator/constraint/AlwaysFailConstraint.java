package org.voidbucket.validator.constraint;

import org.jetbrains.annotations.NotNull;

/**
 * A {@link Constraint} implementation that always results in {@link ConstraintStatus#FAILED}.
 */
public class AlwaysFailConstraint extends FixedResultConstraint {

    public AlwaysFailConstraint() {
        super(ConstraintStatus.FAILED);
    }

    public AlwaysFailConstraint(final @NotNull ReadinessEvaluator middlewareChain) {
        super(ConstraintStatus.FAILED, middlewareChain);
    }

}
