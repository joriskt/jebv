package org.voidbucket.validator.constraint;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.voidbucket.validator.reflect.invoke.ConstraintInvoker;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * <p>A simple {@link Constraint} implementation that takes a {@link Runnable} and runs it.</p>
 * <p>The {@link ReadinessEvaluator} can be customized through the constructor parameters.</p>
 */
public final class RunnableConstraint implements Constraint {

    private final Runnable runnable;
    private final ReadinessEvaluator middlewareChain;

    public RunnableConstraint(final @NotNull Runnable runnable) {
        this(runnable, new ReadinessEvaluatorChain());
    }

    public RunnableConstraint(final @NotNull Runnable runnable,
                              final @NotNull ReadinessEvaluator middlewareChain) {
        this.runnable = Objects.requireNonNull(runnable, "runnable must not be null");
        this.middlewareChain = Objects.requireNonNull(middlewareChain, "middlewareChain must not be null");
    }

    public void evaluate() {
        runnable.run();
    }

    @Override
    public Object getInstance() {
        return this;
    }

    @Override
    @SneakyThrows
    public Method getMethod() {
        return getClass().getMethod("evaluate");
    }

    @Override
    public ReadinessEvaluator getReadinessEvaluator() {
        return middlewareChain;
    }

    @Override
    public @Nullable Class<? extends ConstraintInvoker> getDesiredInvoker() {
        return null;
    }

}
