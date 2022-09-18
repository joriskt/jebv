package org.voidbucket.validator.scanner;

import java.lang.annotation.Annotation;

public interface ConstraintPrototypeMutator {

    void mutate(Annotation annotation, ScannedConstraintPrototype prototype);

}
