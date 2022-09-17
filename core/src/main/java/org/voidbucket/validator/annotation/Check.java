package org.voidbucket.validator.annotation;

import org.voidbucket.validator.Validator;
import org.voidbucket.validator.constraint.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p>Marks this method as a {@link Constraint} that can be used to validate the subject classes given in
 * {@link #value()}.</p>
 * <p>If {@link #groups()} is specified, this check will only be run if whichever {@link Validator} is evaluating
 * this {@link Constraint} matches one or more of the given groups.</p>
 */
@Retention(RUNTIME)
@Target({METHOD})
public @interface Check {

    /**
     * The list of subjects for which this check should be triggered.
     * @return A list of subject classes.
     */
    Class<?>[] value() default {};

    /**
     * The set of groups that this {@link Constraint} should be enabled for.
     * @return A list of groups.
     */
    Class<?>[] groups() default {};

}
