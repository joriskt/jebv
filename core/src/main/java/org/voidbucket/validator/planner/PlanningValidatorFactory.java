package org.voidbucket.validator.planner;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.voidbucket.validator.Validator;
import org.voidbucket.validator.ValidatorFactory;
import org.voidbucket.validator.constraint.ConstraintDiscoverer;
import org.voidbucket.validator.discover.NoOpConstraintDiscoverer;
import org.voidbucket.validator.planner.impl.NoOpConstraintSorter;

import java.util.HashMap;
import java.util.Map;

public class PlanningValidatorFactory implements ValidatorFactory {

    private final ConstraintDiscoverer discoverer;


    private final Map<Class<?>, Plan> planCache;

    private PlanningValidatorFactory(final ConstraintDiscoverer discoverer) {
        this.discoverer = discoverer;
        this.planCache = new HashMap<>();
    }

    @NotNull
    private Plan buildPlanFor(final Class<?> subject) {
        // First, we discover all constraints.


        return new Plan();
    }

    @NotNull
    private Plan getPlanFor(final Class<?> subject) {
        return planCache.computeIfAbsent(subject, this::buildPlanFor);
    }

    @Override
    public @NotNull <T> Validator<T> createValidator(@NotNull Class<T> subjectType) {
        return new PlanningValidator<T>(getPlanFor(subjectType));
    }

    public PlanningValidatorFactory.Configurer configure() {
        return new Configurer();
    }

    @Getter
    @Setter
    @Accessors(fluent = true, chain = true)
    public static class Configurer {

        private ConstraintDiscoverer discoverer;
        private ConstraintSorter sorter;

        private Configurer() {
            this.discoverer = new NoOpConstraintDiscoverer();
            this.sorter = new NoOpConstraintSorter();
        }

    }

}
