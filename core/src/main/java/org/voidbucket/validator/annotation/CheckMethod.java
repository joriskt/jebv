package org.voidbucket.validator.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Marks this method as a check that should be run.
 */
@Retention(RUNTIME)
@Target({METHOD})
public @interface CheckMethod {
//    Class<?>[] groups() default {};
}
