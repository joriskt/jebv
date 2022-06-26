package org.voidbucket.validator.impl.simple;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.voidbucket.validator.ContextFactory;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintDiscoverer;
import org.voidbucket.validator.reflect.invoke.MethodInvoker;
import org.voidbucket.validator.constraint.ConstraintValidator;
import org.voidbucket.validator.reflect.invoke.ContextualMethodInvoker;
import org.voidbucket.validator.reflect.invoke.NoArgsMethodInvoker;
import org.voidbucket.validator.impl.DefaultContextFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Accessors(chain = true)
class SimpleValidatorConfig implements Cloneable {

    private ContextFactory contextFactory;

    private Set<? extends ConstraintDiscoverer> discoverers;
    private Set<? extends ConstraintValidator> validators;

    private MethodInvoker defaultInvoker;
    private Set<? extends MethodInvoker> invokers;
    private Map<Class<? extends MethodInvoker>, MethodInvoker> invokerMap;

    public SimpleValidatorConfig() {
        this.contextFactory = new DefaultContextFactory();

        this.discoverers = new HashSet<>();
        this.validators = new HashSet<>();

        this.defaultInvoker = new ContextualMethodInvoker();
        this.invokers = new HashSet<>(List.of(
            defaultInvoker,
            new NoArgsMethodInvoker()
        ));
    }

    MethodInvoker getOrDefault(final Class<? extends MethodInvoker> invokerType) {
        return invokerMap.getOrDefault(invokerType, defaultInvoker);
    }

    Set<Constraint> discoverConstraints(final Class<?> subjectType) {
        final Set<Constraint> constraints = new HashSet<>();

        // Discover based on the subject type.
        for (final ConstraintDiscoverer discoverer : discoverers) {
            constraints.addAll(discoverer.discover(subjectType));
        }

        return constraints;
    }

    @Override
    @SneakyThrows
    public SimpleValidatorConfig clone() {
        final SimpleValidatorConfig clone = (SimpleValidatorConfig) super.clone();
        clone.discoverers = new HashSet<>(discoverers);
        clone.validators = new HashSet<>(validators);
        clone.invokers = new HashSet<>(invokers);
        return clone;
    }

}
