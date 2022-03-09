package org.voidbucket.validator.annotate;

import org.voidbucket.validator.Context;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that this method, when invoked, provides the given classes to the {@link Context}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Provides {
    Class<?>[] value();
}
