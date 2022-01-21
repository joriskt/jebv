package org.voidbucket.validator.check;

import org.voidbucket.validator.ValidatorContext;

public class BasicCheck implements Check {

    @Override
    public boolean canRun(ValidatorContext context) {
        return false;
    }

    @Override
    public boolean shouldRun(ValidatorContext context) {
        return false;
    }

}
