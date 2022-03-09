package org.voidbucket.validator.constraint;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.voidbucket.validator.Context;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

/**
 * A single constraint that should be validated.
 */
public interface Constraint {

    Method getMethod();

    ConstraintMiddlewareChain getMiddlewares();

    Class<? extends ConstraintValidator> getValidatorType();

    /**
     * Gets the unique name of this {@link Constraint}, or <b>null</b> if there is none.
     * @return a unique name for this {@link Constraint}
     */
    default @Nullable String getName() {
        return null;
    }

    /**
     * Returns a list of types that must be present in the {@link Context} prior
     * to this constraint being evaluated.
     * @return the list of required types
     */
    default @NotNull Collection<Class<?>> getRequiredTypes() {
        return List.of();
    }

    /**
     * Returns a list of types that will be provided to the {@link Context} upon
     * successful evaluation of this constraint.
     * @return the list of provided types
     */
    default @NotNull Collection<Class<?>> getProvidedTypes() {
        return List.of();
    }

    /**
     * Gets the desired {@link ConstraintInvoker} that this constraints want to use.
     */
    default @Nullable Class<? extends ConstraintInvoker> getDesiredInvoker() {
        return null;
    }

}
