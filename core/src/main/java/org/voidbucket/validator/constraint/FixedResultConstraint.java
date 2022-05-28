package org.voidbucket.validator.constraint;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.voidbucket.validator.constraint.readiness.ReadinessEvaluatorChain;
import org.voidbucket.validator.exception.result.ConstraintStatusException;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * <p>A simple {@link Constraint} implementation that takes a {@link ConstraintStatus} and returns it as the
 * result of the evaluation.</p>
 * <p>The {@link ReadinessEvaluator} can be customized through the constructor parameters.</p>
 */
public class FixedResultConstraint implements Constraint {

    private final ConstraintStatus outcome;
    private final ReadinessEvaluator readinessEvaluator;

    public FixedResultConstraint(final @NotNull ConstraintStatus outcome) {
        this(outcome, new ReadinessEvaluatorChain());
    }

    public FixedResultConstraint(final @NotNull ConstraintStatus outcome,
                                 final @NotNull ReadinessEvaluator readinessEvaluator) {
        this.outcome = Objects.requireNonNull(outcome, "outcome must not be null");
        this.readinessEvaluator = Objects.requireNonNull(
            readinessEvaluator, "readinessEvaluator must not be null");
    }

    public void evaluate() {
        throw new ConstraintStatusException(outcome);
    }

    @Override
    @SneakyThrows
    public Method getMethod() {
        return getClass().getMethod("evaluate");
    }

    @Override
    public ReadinessEvaluator getReadinessEvaluator() {
        return readinessEvaluator;
    }

}
