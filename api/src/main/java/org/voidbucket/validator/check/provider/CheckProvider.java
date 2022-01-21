package org.voidbucket.validator.check.provider;

import org.voidbucket.validator.check.Check;

/**
 * Provides an instance of a Check when given a Check class.
 */
public interface CheckProvider {
    Check provide(Class<?> checkerType);
}
