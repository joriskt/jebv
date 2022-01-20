package org.voidbucket.validator.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/***
 * Used to indicate that this parameter
 */
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface Subject {
}
