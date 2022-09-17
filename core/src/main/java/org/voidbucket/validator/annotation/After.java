package org.voidbucket.validator.annotation;

import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintStatus;
import org.voidbucket.validator.constraint.Readiness;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Indicates that this {@link Constraint} will not reach a {@link Readiness#READY} status until all specified
 * {@link Constraint}s have reached a finalized state.</p>
 * <p><b>Warning:</b> <i>Finalized</i> can also mean {@link ConstraintStatus#FAILED} or
 * {@link ConstraintStatus#UNREACHABLE}. If a successful outcome is required, use @{@link Depends} instead.</p>
 * @see ConstraintStatus
 * @see Depends
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface After {

    /**
     * The {@link Constraint}s that should reach a finalized state before this {@link Constraint} is evaluated.
     * @return The {@link Constraint} types that should run first.
     */
    Class<?>[] value();

}
