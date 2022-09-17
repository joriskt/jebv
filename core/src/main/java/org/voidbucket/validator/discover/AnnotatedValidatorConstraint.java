package org.voidbucket.validator.discover;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.voidbucket.validator.annotation.After;
import org.voidbucket.validator.annotation.Depends;
import org.voidbucket.validator.constraint.*;
import org.voidbucket.validator.constraint.ChronologyReadinessEvaluator;
import org.voidbucket.validator.constraint.dependency.DependencyReadinessEvaluator;
import org.voidbucket.validator.constraint.ReadinessEvaluatorChain;
import org.voidbucket.validator.constraint.ReadinessEvaluators;
import org.voidbucket.validator.constraint.reference.ConstraintReferences;
import org.voidbucket.validator.reflect.invoke.ConstraintInvoker;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * The {@link Constraint} implementation outputted by the {@link AnnotatedValidatorConstraintDiscoverer}.
 */
@Getter
@Setter
public class AnnotatedValidatorConstraint implements Constraint {

    private static final Map<Class<? extends Annotation>, BiConsumer<AnnotatedValidatorConstraint, Annotation>>
        mutators;

    public static void registerMutator(final Class<? extends Annotation> annotationType,
                                       final BiConsumer<AnnotatedValidatorConstraint, Annotation> mutator) {
        if (mutators.containsKey(annotationType)) {
            throw new IllegalArgumentException("Attempt to override mutator for annotation: " +
                                               annotationType.getCanonicalName());
        }
        mutators.put(annotationType, mutator);
    }

    public static BiConsumer<AnnotatedValidatorConstraint, Annotation> getMutator(
        final Class<? extends Annotation> annotationType) {
        return mutators.get(annotationType);
    }

    private static ReadinessEvaluatorChain getReadinessChain(final Constraint constraint) {
        final ReadinessEvaluator evaluator = constraint.getReadinessEvaluator();
        if (evaluator instanceof ReadinessEvaluatorChain chain) {
            return chain;
        }
        throw new IllegalArgumentException(
            "Cannot append AnnotatedValidatorConstraint mutation to non-chaining ReadinessEvaluator");
    }

    static {
        mutators = new HashMap<>();
        mutators.put(Depends.class, ((constraint, annotation) -> {
            final Depends depends = (Depends) annotation;
            final Class<?>[] values = depends.value();
            final Set<ConstraintStatus> permitted = Set.of(depends.permitted());
            final Readiness otherwise = depends.otherwise();

            // Find an existing DependencyReadinessEvaluator or add a new one to the chain.
            final DependencyReadinessEvaluator chain = constraint
                .getReadinessEvaluatorChain()
                .findOrRegister(DependencyReadinessEvaluator.class, DependencyReadinessEvaluator::new);

            // Register every type that this Constraint depends on.
            for (final Class<?> type : values) {
                chain.register(ConstraintReferences.ofType(type), (builder) -> {
                    // When registering a dependency, we specify explicitly what to do on any given possible
                    // ConstraintStatus. Ready by default, and the otherwise() value in all other cases.
                    for (ConstraintStatus status : ConstraintStatus.values()) {
                        builder.map(status, permitted.contains(status) ? Readiness.READY : otherwise);
                    }
                    return builder.build();
                });
            }
        }));
        mutators.put(After.class, ((constraint, annotation) -> {
            final After after = (After) annotation;

            // Find an existing ChronologyReadinessEvaluator or add a new one to the chain.
            final ChronologyReadinessEvaluator evaluator = constraint
                .getReadinessEvaluatorChain()
                .findOrRegister(ChronologyReadinessEvaluator.class, ChronologyReadinessEvaluator::new);

            // Register every specified dependency type
            for (final Class<?> type : after.value()) {
                evaluator.after(ConstraintReferences.ofType(type));
            }
        }));
    }

    private final ConstraintValidator validator;
    private final Method method;
    private final ReadinessEvaluatorChain readinessEvaluatorChain;

    private Class<? extends ConstraintInvoker> desiredInvoker;
    private String name;
    private List<Class<?>> requiredTypes;
    private List<Class<?>> providedTypes;

    AnnotatedValidatorConstraint(final ConstraintValidator validator,
                                 final Method method) {
        this.validator = validator;
        this.method = method;
        this.readinessEvaluatorChain = ReadinessEvaluators.chain();

        readinessEvaluatorChain.register(new DependencyReadinessEvaluator());
    }

    @Override
    public @Nullable Class<? extends ConstraintInvoker> getDesiredInvoker() {
        return desiredInvoker;
    }

    @Override
    public Object getInstance() {
        return this;
    }

    @Override
    public ReadinessEvaluator getReadinessEvaluator() {
        return readinessEvaluatorChain;
    }

    @Override
    public @Nullable String getName() {
        return name;
    }

    @Override
    public @NotNull Collection<Class<?>> getRequiredTypes() {
        return requiredTypes;
    }

    @Override
    public @NotNull Collection<Class<?>> getProvidedTypes() {
        return providedTypes;
    }

}
