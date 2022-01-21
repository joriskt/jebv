package org.voidbucket.validator.check;

import org.voidbucket.validator.ValidatorContext;

/**
 * This is an instance of a class that runs checks.
 */
public interface Check {
    boolean canRun(ValidatorContext context);
    boolean shouldRun(ValidatorContext context);
}
