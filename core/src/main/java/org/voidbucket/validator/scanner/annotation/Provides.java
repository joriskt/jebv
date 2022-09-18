package org.voidbucket.validator.scanner.annotation;

import org.voidbucket.validator.Context;
import org.voidbucket.validator.scanner.mutator.Mutator;
import org.voidbucket.validator.scanner.mutator.ProvidesPrototypeMutator;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * Indicates that this method, when invoked, provides the given classes to the {@link Context}.
 */
@Target({ METHOD })
@Retention(RUNTIME)
@Mutator(appliedBy = ProvidesPrototypeMutator.class)
public @interface Provides {

    Class<?>[] value();

}
