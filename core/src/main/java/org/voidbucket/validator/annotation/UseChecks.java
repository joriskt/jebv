package org.voidbucket.validator.annotation;

import org.voidbucket.validator.check.Check;
import org.voidbucket.validator.check.discover.AnnotationCheckDiscoverer;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Specifies what classes to scan when using the {@link AnnotationCheckDiscoverer}.
 */
@Retention(RUNTIME)
@Target({TYPE, FIELD})
public @interface UseChecks {
    Class<? extends Check>[] value();
}
