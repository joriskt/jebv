package org.voidbucket.validator.check;

import org.voidbucket.validator.ValidatorContext;

public interface CheckRunner {
    void prepare(final ValidatorContext validatorContext);
    void execute(final ValidatorContext validatorContext);
}
