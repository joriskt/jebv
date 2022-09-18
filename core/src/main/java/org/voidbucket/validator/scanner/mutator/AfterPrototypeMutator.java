package org.voidbucket.validator.scanner.mutator;

import org.voidbucket.validator.scanner.annotation.After;
import org.voidbucket.validator.constraint.readiness.ChronologyReadinessEvaluator;
import org.voidbucket.validator.constraint.reference.ConstraintReferences;
import org.voidbucket.validator.scanner.ScannedConstraintPrototype;
import org.voidbucket.validator.scanner.ConstraintPrototypeMutator;

import java.lang.annotation.Annotation;

public final class AfterPrototypeMutator implements ConstraintPrototypeMutator {

    @Override
    public void mutate(final Annotation annotation,
                       final ScannedConstraintPrototype prototype) {
        final After after = (After) annotation;

        // Find an existing ChronologyReadinessEvaluator or add a new one to the chain.
        final ChronologyReadinessEvaluator evaluator = prototype
            .getReadinessEvaluatorChain()
            .findOrRegister(ChronologyReadinessEvaluator.class, ChronologyReadinessEvaluator::new);

        // Register every specified dependency type
        for (final Class<?> type : after.value()) {
            evaluator.after(ConstraintReferences.ofType(type));
        }
    }

}
