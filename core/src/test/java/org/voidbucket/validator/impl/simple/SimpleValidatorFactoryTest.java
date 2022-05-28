package org.voidbucket.validator.impl.simple;

import org.junit.jupiter.api.Test;
import org.voidbucket.validator.Validator;
import org.voidbucket.validator.constraint.AlwaysPassConstraint;
import org.voidbucket.validator.discover.FixedListConstraintDiscoverer;
import org.voidbucket.validator.example.Empty;

import static org.junit.jupiter.api.Assertions.*;

class SimpleValidatorFactoryTest {

    @Test
    void createValidator_whenUsingDefaults_shouldCreateValidator() {
        final SimpleValidatorFactory factory = new SimpleValidatorFactory();
        final Validator<Empty> validator = factory.createValidator(Empty.class);

        assertNotNull(validator);
    }

    @Test
    void createValidator_whenUsingFixedConstraint_shouldCreateValidator() {
        // Arrange
        final SimpleValidatorFactory factory = new SimpleValidatorFactory();
        factory.constraintDiscoverer(new FixedListConstraintDiscoverer(
            new AlwaysPassConstraint()
        ));

        // Act & Assert
        final Validator<Empty> validator = factory.createValidator(Empty.class);
        assertNotNull(validator);
        assertInstanceOf(SimpleValidator.class, validator);
    }

}
