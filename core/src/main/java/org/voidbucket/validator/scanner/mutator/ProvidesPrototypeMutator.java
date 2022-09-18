package org.voidbucket.validator.scanner.mutator;

import org.voidbucket.validator.scanner.ScannedConstraintPrototype;
import org.voidbucket.validator.scanner.ConstraintPrototypeMutator;
import org.voidbucket.validator.scanner.annotation.Provides;

import java.lang.annotation.Annotation;

public final class ProvidesPrototypeMutator implements ConstraintPrototypeMutator {

    @Override
    public void mutate(final Annotation annotation,
                       final ScannedConstraintPrototype prototype) {
        final Provides provides = (Provides) annotation;



    }

}
