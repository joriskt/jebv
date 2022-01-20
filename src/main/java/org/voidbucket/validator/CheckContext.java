package org.voidbucket.validator;

import org.voidbucket.validator.check.CheckDefinition;

public interface CheckContext extends ValidatorContext {
    CheckDefinition getCheckDefinition();
}
