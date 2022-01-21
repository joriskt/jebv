package org.voidbucket.validator;

import org.voidbucket.validator.violation.Violation;

public interface ValidatorContext {

    Validator getValidator();
    void raise(Violation violation);

}
