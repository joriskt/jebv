package org.voidbucket.validator.impl;

import org.junit.jupiter.api.Test;
import org.voidbucket.validator.Validator;
import org.voidbucket.validator.impl.basic.BasicAnnotationBasedValidator;
import org.voidbucket.validator.model.Root;

class BasicAnnotationBasedValidatorTest {

    @Test
    public void validate_test() {
        // Arrange
        final Validator validator = new BasicAnnotationBasedValidator();

        // Act
        validator.validate(new Root());
    }

}
