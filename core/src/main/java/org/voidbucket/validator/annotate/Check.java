package org.voidbucket.validator.annotate;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Marks this method as a check that should be run.
 */
@Retention(RUNTIME)
@Target({METHOD})
public @interface Check {

    /**
     * The list of subjects for which this check should be triggered.
     * @return A list of subject classes.
     */
    Class<?>[] value() default {};
    Class<?>[] groups() default {};

}
