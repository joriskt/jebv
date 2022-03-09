package org.voidbucket.validator.impl.simple;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.voidbucket.validator.Validator;
import org.voidbucket.validator.ValidatorFactory;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintInvoker;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
public final class SimpleValidatorFactory implements ValidatorFactory {

    private final SimpleValidatorConfig config;

    public SimpleValidatorFactory() {
        this(new SimpleValidatorConfig());
    }

    @Override
    public @NotNull <T> Validator<T> createValidator(@NotNull Class<T> subjectType) {
        Objects.requireNonNull(subjectType, "cannot construct validator for null type");

        // Prepare.
        final Set<Constraint> constraints = config.discoverConstraints(subjectType);
        final Map<Class<? extends ConstraintInvoker>, ConstraintInvoker> invokerMap =
            buildInvokerMap(config.getInvokers());

        // Build!
        return new SimpleValidator<T>(constraints, config.getDefaultInvoker(), invokerMap);
    }

    private Map<Class<? extends ConstraintInvoker>, ConstraintInvoker> buildInvokerMap(final Set<? extends ConstraintInvoker> invokers) {
        final Map<Class<? extends ConstraintInvoker>, ConstraintInvoker> map = new HashMap<>();
        invokers.forEach(invoker -> map.put(invoker.getClass(), invoker));
        return map;
    }

}
