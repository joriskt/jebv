package org.voidbucket.validator.constraint;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.voidbucket.validator.Context;
import org.voidbucket.validator.constraint.readiness.ReadinessEvaluatorChain;
import org.voidbucket.validator.reflect.invoke.ContextualConstraintInvoker;
import org.voidbucket.validator.reflect.invoke.ConstraintInvoker;
import org.voidbucket.validator.violation.Violation;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * <p>This {@link Constraint} implementation takes a list of {@link Violation}s and returns them during evaluation.</p>
 * <p>The {@link ReadinessEvaluator} can be customized through the constructor parameters.</p>
 */
public final class FixedViolationsConstraint implements Constraint {

    private final List<Violation> violations;
    private final ReadinessEvaluator middlewareChain;

    public FixedViolationsConstraint(final @NotNull Violation ...violations) {
        this(List.of(violations));
    }

    public FixedViolationsConstraint(final @NotNull List<Violation> violations) {
        this(violations, new ReadinessEvaluatorChain());
    }

    public FixedViolationsConstraint(final @NotNull List<Violation> violations,
                                     final @NotNull ReadinessEvaluator middlewareChain) {
        this.violations = Objects.requireNonNull(violations, "violations must not be null");
        this.middlewareChain = Objects.requireNonNull(middlewareChain, "middlewareChain must not be null");
    }

    public void evaluate(final Context context) {
        for (final Violation violation : violations) {
            context.raise(violation);
        }
    }

    @Override
    public Object getInstance() {
        return this;
    }

    @Override
    @SneakyThrows
    public Method getMethod() {
        return getClass().getDeclaredMethod("evaluate", Context.class);
    }

    @Override
    public ReadinessEvaluator getReadinessEvaluator() {
        return middlewareChain;
    }

    @Override
    public Class<? extends ConstraintInvoker> getDesiredInvoker() {
        return ContextualConstraintInvoker.class;
    }

}
