package org.voidbucket.validator.impl.stateful;

import org.voidbucket.validator.Validator;
import org.voidbucket.validator.ValidatorContext;
import org.voidbucket.validator.Violation;

class StatefulValidatorContext implements ValidatorContext {

    private final StatefulValidator validator;
    private final Object state;

    public StatefulValidatorContext(final StatefulValidator validator,
                                    final Object state) {
        this.validator = validator;
        this.state = state;
    }

    @Override
    public Validator getValidator() {
        return validator;
    }

    @Override
    public void raise(Violation violation) {

    }

}
