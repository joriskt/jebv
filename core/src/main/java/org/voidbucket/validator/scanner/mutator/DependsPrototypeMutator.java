package org.voidbucket.validator.scanner.mutator;

import org.voidbucket.validator.constraint.ConstraintStatus;
import org.voidbucket.validator.constraint.Readiness;
import org.voidbucket.validator.constraint.dependency.DependencyReadinessEvaluator;
import org.voidbucket.validator.constraint.reference.ConstraintReferences;
import org.voidbucket.validator.scanner.ScannedConstraintPrototype;
import org.voidbucket.validator.scanner.ConstraintPrototypeMutator;
import org.voidbucket.validator.scanner.annotation.Depends;

import java.lang.annotation.Annotation;
import java.util.Set;

public final class DependsPrototypeMutator implements ConstraintPrototypeMutator {

    @Override
    public void mutate(final Annotation annotation,
                       final ScannedConstraintPrototype prototype) {
        final Depends depends = (Depends) annotation;
        final Class<?>[] values = depends.value();
        final Set<ConstraintStatus> permitted = Set.of(depends.permitted());
        final Readiness otherwise = depends.otherwise();

        // Find an existing DependencyReadinessEvaluator or add a new one to the chain.
        final DependencyReadinessEvaluator chain = prototype
            .getReadinessEvaluatorChain()
            .findOrRegister(DependencyReadinessEvaluator.class, DependencyReadinessEvaluator::new);

        // Register every type that this Constraint depends on.
        for (final Class<?> type : values) {
            chain.register(ConstraintReferences.ofType(type), (builder) -> {
                // When registering a dependency, we specify explicitly what to do on any given possible
                // ConstraintStatus. Ready by default, and the otherwise() value in all other cases.
                for (ConstraintStatus status : ConstraintStatus.values()) {
                    builder.map(status, permitted.contains(status) ? Readiness.READY : otherwise);
                }
                return builder.build();
            });
        }
    }

}
