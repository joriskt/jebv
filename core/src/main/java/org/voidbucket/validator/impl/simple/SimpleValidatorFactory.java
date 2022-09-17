package org.voidbucket.validator.impl.simple;

import one.util.streamex.StreamEx;
import org.jetbrains.annotations.NotNull;
import org.voidbucket.validator.ContextFactory;
import org.voidbucket.validator.Validator;
import org.voidbucket.validator.ValidatorFactory;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintDiscoverer;
import org.voidbucket.validator.reflect.invoke.ConstraintInvoker;
import org.voidbucket.validator.constraint.ConstraintValidator;
import org.voidbucket.validator.reflect.invoke.ContextualConstraintInvoker;
import org.voidbucket.validator.reflect.invoke.NoArgsConstraintInvoker;
import org.voidbucket.validator.impl.DefaultContextFactory;

import java.util.*;

public final class SimpleValidatorFactory implements ValidatorFactory {

    private ContextFactory contextFactory;

    private Set<ConstraintValidator> constraintValidators;
    private Set<ConstraintDiscoverer> constraintDiscoverers;

    private ConstraintInvoker defaultInvoker;
    private Set<ConstraintInvoker> invokers;
    private Map<Class<? extends ConstraintInvoker>, ConstraintInvoker> invokerMap;

    public SimpleValidatorFactory() {
        this.contextFactory = new DefaultContextFactory();
        this.constraintDiscoverers = new HashSet<>();

        this.defaultInvoker = new ContextualConstraintInvoker();
        this.invokers = new HashSet<>(List.of(
            defaultInvoker,
            new NoArgsConstraintInvoker()
        ));
        initInvokerMap();
    }

    private void initInvokerMap() {
        this.invokerMap = new HashMap<>();
        for (final ConstraintInvoker invoker : invokers) {
            invokerMap.put(invoker.getClass(), invoker);
        }
    }

    public SimpleValidatorFactory contextFactory(final @NotNull ContextFactory contextFactory) {
        Objects.requireNonNull(contextFactory, "contextFactory must not be null");
        this.contextFactory = contextFactory;
        return this;
    }

    public SimpleValidatorFactory constraintDiscoverers(final @NotNull Set<ConstraintDiscoverer> discoverers) {
        Objects.requireNonNull(discoverers, "discoverers must not be null");
        this.constraintDiscoverers = discoverers;
        return this;
    }

    public SimpleValidatorFactory constraintDiscoverer(final @NotNull ConstraintDiscoverer discoverer) {
        Objects.requireNonNull(discoverer, "discoverer must not be null");
        this.constraintDiscoverers.add(discoverer);
        return this;
    }

    public SimpleValidatorFactory defaultInvoker(final @NotNull ConstraintInvoker defaultInvoker) {
        this.defaultInvoker = Objects.requireNonNull(defaultInvoker);
        return this;
    }

    public SimpleValidatorFactory invoker(final @NotNull ConstraintInvoker invoker) {
        Objects.requireNonNull(invoker);
        if (invokerMap.containsKey(invoker.getClass())) {
            throw new IllegalArgumentException(
                "Invoker of type \"" + invoker.getClass().getCanonicalName() +
                "\" is already registered. Consider calling #unregisterInvoker(Class<? extends ConstraintInvoker> first.");
        }

        invokers.add(invoker);
        invokerMap.put(invoker.getClass(), invoker);
        return this;
    }

    public SimpleValidatorFactory invokers(final @NotNull Collection<ConstraintInvoker> invokers) {
        Objects.requireNonNull(invokers, "invokers must not be null");
        this.invokers = new HashSet<>(invokers);
        initInvokerMap();
        return this;
    }

    public SimpleValidatorFactory removeInvoker(final @NotNull ConstraintInvoker invoker) {
        Objects.requireNonNull(invoker, "invoker must not be null");
        return removeInvoker(invoker.getClass());
    }

    public SimpleValidatorFactory removeInvoker(final @NotNull Class<? extends ConstraintInvoker> invokerType) {
        Objects.requireNonNull(invokerType, "invokerType must not be null");
        invokers.removeIf(invoker -> invoker.getClass().equals(invokerType));
        invokerMap.remove(invokerType);
        return this;
    }

    public SimpleValidatorFactory clearInvokers() {
        this.invokers.clear();
        this.invokerMap.clear();
        return this;
    }

    @Override
    public @NotNull <T> Validator<T> createValidator(@NotNull Class<T> subjectType) {
        Objects.requireNonNull(subjectType, "subjectType must not be null");

        // Discover constraints.
        final List<Constraint> constraints = StreamEx.of(constraintDiscoverers)
            .flatMap(discoverer -> discoverer.discover(subjectType).stream())
            .map(constraint -> (Constraint) constraint)
            .toList();

        // Build the validator!
        return SimpleValidator.<T>builder()
            .constraints(constraints)
            .subjectType(subjectType)
            .contextFactory(contextFactory)
            .defaultInvoker(defaultInvoker)
            .invokerMap(new HashMap<>(invokerMap))
            .build();
    }

}
