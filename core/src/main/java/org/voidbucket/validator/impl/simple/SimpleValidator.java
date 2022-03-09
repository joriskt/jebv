package org.voidbucket.validator.impl.simple;

import org.jetbrains.annotations.NotNull;
import org.voidbucket.validator.Context;
import org.voidbucket.validator.ValidationReport;
import org.voidbucket.validator.Validator;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintInvoker;
import org.voidbucket.validator.constraint.ConstraintReadiness;
import org.voidbucket.validator.constraint.ConstraintStatus;

import java.util.*;

public final class SimpleValidator<T> implements Validator<T> {

    private final Set<Constraint> constraints;
    private final ConstraintInvoker defaultInvoker;
    private final Map<Class<? extends ConstraintInvoker>, ConstraintInvoker> invokerMap;

    SimpleValidator(final Set<Constraint> constraints,
                    final ConstraintInvoker defaultInvoker,
                    final Map<Class<? extends ConstraintInvoker>, ConstraintInvoker> invokerMap) {
        this.constraints = constraints;
        this.defaultInvoker = Objects.requireNonNull(defaultInvoker, "default invoker must not be null");
        this.invokerMap = invokerMap;
    }

    private ConstraintInvoker findInvoker(final Class<? extends ConstraintInvoker> invokerType) {
        return invokerMap.getOrDefault(invokerType, defaultInvoker);
    }

    @Override
    public @NotNull ValidationReport validate(final @NotNull T subject) {
        final SimpleValidatorState state = new SimpleValidatorState(constraints);
        final Context context = new Context();

        // Group the constraints into evaluated and remaining.
        final Set<Constraint> evaluated = new HashSet<>();
        final Set<Constraint> remaining = new HashSet<>(constraints);

        // Keep looping until we fail to find a runnable constraint.
        while (!remaining.isEmpty()) {

            // Find the next runnable constraint.
            final Constraint next = findReadyConstraint(evaluated, remaining, state);
            if (next == null) {
                break;
            }

            // Find the matching invoker, or the default.
            final ConstraintInvoker invoker = invokerMap
                .getOrDefault(next.getDesiredInvoker(), defaultInvoker)
                .invoke(next, context);

            final ConstraintStatus status =
        }

        // TODO: Build validation report. If !remaining.isEmpty() then the validation failed.
        return null;
    }

    private Constraint findReadyConstraint(final Set<Constraint> evaluated,
                                           final Set<Constraint> remaining,
                                           final SimpleValidatorState state) {
        final Iterator<Constraint> iterator = remaining.iterator();
        while (iterator.hasNext()) {
            final Constraint constraint = iterator.next();

            // Evaluate the constraint middlewares to determine the readiness of this constraint.
            final ConstraintReadiness readiness = constraint
                .getMiddlewares()
                .evaluate(state);

            switch (readiness) {
                case READY -> {
                    return constraint;
                }
                case SKIP, FAIL, NEVER -> {
                    // All of these statuses indicate that the constraint need not be run.
                    state.setStatus(constraint, readiness.toStatus());
                    evaluated.add(constraint);
                    iterator.remove();
                }
                case WAIT -> {
                    // This is the only case where we do nothing.
                }
            }
        }

    }

    private ConstraintReadiness evaluateReadiness(final Constraint constraint,
                                                  final SimpleValidatorState state) {
        return constraint.getMiddlewares().evaluate(state);
    }

}
