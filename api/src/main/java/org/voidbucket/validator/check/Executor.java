package org.voidbucket.validator.check;

import org.voidbucket.validator.ValidatorContext;

public interface Executor {
    void execute(Check check, ValidatorContext context);
}
