package org.voidbucket.validator.discover;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.voidbucket.validator.constraint.*;
import org.voidbucket.validator.constraint.readiness.DependencyReadinessEvaluator;
import org.voidbucket.validator.constraint.readiness.ReadinessEvaluatorChain;
import org.voidbucket.validator.constraint.readiness.ReadinessEvaluators;
import org.voidbucket.validator.reflect.MethodInvoker;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * The {@link Constraint} implementation outputted by the {@link AnnotatedValidatorConstraintDiscoverer}.
 */
@Getter
@Setter()
public class AnnotatedValidatorConstraint implements Constraint {

    private static final Map<Class<? extends Annotation>, BiConsumer<Constraint, Annotation>>
        mutators;

    public static void registerMutator(final Class<? extends Annotation> annotationType,
                                       final BiConsumer<Constraint, Annotation> mutator) {
        if (mutators.containsKey(annotationType)) {
            throw new IllegalArgumentException("Attempt to override mutator for annotation: " +
                                               annotationType.getCanonicalName());
        }
        mutators.put(annotationType, mutator);
    }

    public static BiConsumer<Constraint, Annotation> getMutator(
        final Class<? extends Annotation> annotationType) {
        return mutators.get(annotationType);
    }

    static {
        mutators = new HashMap<>();
//        mutators.put(Depends.class, ((constraint, annotation) -> {
//            final Depends depends = (Depends) annotation;
//            final Class<?>[] values = depends.value();
//            final Set<ConstraintStatus> permitted = Set.of(depends.permittedStatusses());
//            final ConstraintReadiness otherwise = depends.otherwise();
//
//            final DependencyReadinessMiddleware middleware = constraint.getReadinessEvaluator()
//                .findOrRegister(DependencyReadinessMiddleware.class, DependencyReadinessMiddleware::new);
//
//            for (final Class<?> type : values) {
//                middleware.register(ConstraintReferences.ofType(type), (builder) -> {
//                    for (ConstraintStatus status : ConstraintStatus.values()) {
//                        if (!permitted.contains(status)) {
//                            builder.action(status, otherwise);
//                        }
//                    }
//                    return builder.build();
//                });
//            }
//
//            constraint.getReadinessEvaluator()
//                .find(DependencyReadinessMiddleware.class);
//        }));
//        mutators.put(After.class, ((constraint, annotation) -> {
//            final After after = (After) annotation;
//            final ChronologyReadinessMiddleware evaluator = constraint.getReadinessEvaluator()
//                .findOrRegister(ChronologyReadinessMiddleware.class, ChronologyReadinessMiddleware::new);
//
//            for (final Class<?> type : after.value()) {
//                evaluator.after(ConstraintReferences.ofType(type));
//            }
//        }));
    }

    private final ConstraintValidator validator;
    private final Method method;
    private final ReadinessEvaluatorChain readinessEvaluatorChain;

    private Class<? extends MethodInvoker> desiredInvoker;
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
    public @Nullable Class<? extends MethodInvoker> getDesiredInvoker() {
        return desiredInvoker;
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
