package org.voidbucket.validator.impl.simple;

import lombok.extern.slf4j.Slf4j;
import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;
import org.jetbrains.annotations.NotNull;
import org.voidbucket.validator.Context;
import org.voidbucket.validator.ContextFactory;
import org.voidbucket.validator.ValidationReport;
import org.voidbucket.validator.ValidatorState;
import org.voidbucket.validator.constraint.*;
import org.voidbucket.validator.constraint.Readiness;
import org.voidbucket.validator.impl.DefaultValidationReport;
import org.voidbucket.validator.reflect.invoke.MethodInvoker;
import org.voidbucket.validator.violation.Violation;

import java.util.*;

@Slf4j
public final class SimpleValidatorRunner implements ValidatorState {

    private final Object subject;
    private final List<Violation> violations;

    // Constraint status
    private final Map<Constraint, ConstraintStatus> statusMap;
    private final List<Constraint> remainingConstraints;

    // Invokers
    private final MethodInvoker defaultInvoker;
    private final Map<Class<? extends MethodInvoker>, MethodInvoker> invokerMap;

    // Context
    private final ContextFactory contextFactory;
    private final Context rootContext;

    SimpleValidatorRunner(final Object subject,
                          final List<Constraint> constraints,
                          final MethodInvoker defaultInvoker,
                          final Map<Class<? extends MethodInvoker>, MethodInvoker> invokerMap,
                          final ContextFactory contextFactory) {
        this.subject = subject;
        this.violations = new ArrayList<>();

        // Constraint status
        this.statusMap = StreamEx.of(constraints)
            .mapToEntry(constraint -> ConstraintStatus.PENDING)
            .toCustomMap(HashMap::new);
        this.remainingConstraints = new LinkedList<>(constraints);

        // Invokers
        this.defaultInvoker = defaultInvoker;
        this.invokerMap = invokerMap;

        // Context
        this.contextFactory = contextFactory;
        this.rootContext = contextFactory.createValidationContext(this);
    }

    boolean hasRemaining() {
        return !remainingConstraints.isEmpty();
    }

    boolean step() {
        final Constraint constraint = findReadyConstraint();
        if (constraint == null) {
            return false;
        }

        final Class<? extends MethodInvoker> desiredInvoker = constraint.getDesiredInvoker();
        final MethodInvoker invoker;

        // Determine the invoker to use.
        if (desiredInvoker != null) {
            if (!invokerMap.containsKey(desiredInvoker)) {
                log.warn("Constraint {} requested invoker of type {}, but it is unavailable. Using default: {}",
                         constraint.getClass().getCanonicalName(),
                         desiredInvoker.getCanonicalName(),
                         defaultInvoker.getClass().getCanonicalName());
            }
            invoker = invokerMap.getOrDefault(desiredInvoker, defaultInvoker);
        } else {
            invoker = defaultInvoker;
        }

        // Evaluate the constraint.
        final Context context = contextFactory.createConstraintContext(rootContext, constraint);
        final Object output = invoker.invoke(constraint.getInstance(), constraint.getMethod(), context);
        final ConstraintStatus status = mapStatus(output);

        // Save the status, regardless of what it is.
        setStatus(constraint, status);

        // If the status is final, move the constraint from 'remaining' to 'evaluated'.
        if (status.isFinalStatus()) {
            remainingConstraints.remove(constraint);
        }

        return true;
    }

    private ConstraintStatus mapStatus(final Object result) {
        if (result == null) {
            return ConstraintStatus.PASSED;
        } else {
            return ConstraintStatus.POSTPONED;
        }
    }

    ValidationReport buildReport() {
        return new DefaultValidationReport(statusMap, violations);
    }

    /**
     * Walks through the list of remaining {@link Constraint}s and evaluates their readiness.
     * Returns the first {@link Constraint} that returns {@link Readiness#READY}.
     * @return the next ready {@link Constraint}, or null if there is none
     */
    private Constraint findReadyConstraint() {
        final Iterator<Constraint> iterator = remainingConstraints.listIterator();
        while (iterator.hasNext()) {
            final Constraint constraint = iterator.next();

            // Evaluate the constraint middlewares to determine the readiness of this constraint.
            final Readiness readiness = constraint
                .getReadinessEvaluator()
                .evaluate(this);

            switch (readiness) {
                case READY -> {
                    return constraint;
                }
                case SKIP, FAIL, NEVER -> {
                    // All of these statuses indicate that the constraint need not be run.
                    setStatus(constraint, readiness.toStatus());
                    iterator.remove();
                }
                case WAIT -> {
                    // Save the POSTPONED status and try the next.
                    setStatus(constraint, readiness.toStatus());
                }
            }
        }
        return null;
    }

    private void setStatus(final Constraint constraint, final ConstraintStatus status) {
        statusMap.put(constraint, status);
    }

    @Override
    public Object getSubject() {
        return subject;
    }

    @Override
    public ConstraintStatus getStatus(final @NotNull Constraint constraint) {
        return statusMap.getOrDefault(constraint, ConstraintStatus.UNREACHABLE);
    }

    @Override
    public ConstraintStatus getStatus(final @NotNull ConstraintReference reference) {
        return EntryStream.of(statusMap)
            .filterKeys(reference::equals)
            .findFirst()
            .map(Map.Entry::getValue)
            .orElse(ConstraintStatus.UNREACHABLE);
    }

    @Override
    public Context getContext() {
        return rootContext;
    }

    @Override
    public void raise(final @NotNull Violation violation) {
        this.violations.add(Objects.requireNonNull(violation, "violation must not be null"));
    }

}
