package org.voidbucket.validator;

import org.junit.jupiter.api.Test;
import org.voidbucket.validator.annotation.UseChecks;
import org.voidbucket.validator.check.Check;
import org.voidbucket.validator.check.discover.AnnotationCheckDiscoverer;
import org.voidbucket.validator.check.discover.CheckDiscoverer;
import org.voidbucket.validator.check.provider.StaticCheckProvider;

class BasicValidatorTest {

    @Test
    public void validate_whenRootCheck_shouldSupplyRootObject() {
        // Arrange
        final CheckDiscoverer discoverer = new AnnotationCheckDiscoverer();
        final StaticCheckProvider checkProvider = new StaticCheckProvider();
        checkProvider.register(new NoOpCheck());
        final Validator validator = new BasicValidator(discoverer, checkProvider);

        // Act
        final SubjectRoot subjectRoot = new SubjectRoot();
        validator.validate(subjectRoot);
    }

    private static final class NoOpCheck implements Check {
        @Override
        public boolean canRun(ValidatorContext context) {
            return false;
        }
        @Override
        public boolean shouldRun(ValidatorContext context) {
            return false;
        }
        @Override
        public void check(ValidatorContext context) {
        }
    }

    @UseChecks({ NoOpCheck.class })
    private static final class SubjectRoot {
        private SubjectChild field;
    }

    private static final class SubjectChild {

    }

}
