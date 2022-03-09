package org.voidbucket.validator.constraint;

import java.lang.annotation.Annotation;

public interface ConstraintMutator {
    void mutate(Constraint constraint, Annotation annotation);
}
