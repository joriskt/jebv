package org.voidbucket.validator;

import org.jetbrains.annotations.NotNull;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintDiscoverer;

/**
 * A {@link Validator} is the product of a {@link ValidatorFactory}. It is responsible for validating
 * {@link Constraint}s that were discovered by the factories' {@link ConstraintDiscoverer}s.
 * @param <T> The type that this {@link Validator} validates.
 */
public interface Validator<T> {

    @NotNull ValidationReport validate(@NotNull T subject);

}
