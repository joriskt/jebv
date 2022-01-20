package org.voidbucket.validator;

public interface ValidatorContext {
    Validator getValidator();
    void raise(Violation violation);
}
