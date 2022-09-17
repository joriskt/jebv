package org.voidbucket.validator.constraint.dependency;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintReference;
import org.voidbucket.validator.constraint.ConstraintStatus;
import org.voidbucket.validator.constraint.Readiness;

import java.util.Map;

/**
 * A single {@link Dependency} of a {@link DependencyReadinessEvaluator}. Contains a {@link ConstraintReference} to the
 * {@link Constraint} that is being depended upon, and a mapping from {@link ConstraintStatus} to {@link Readiness} of
 * this single dependency.
 */
@Getter
@RequiredArgsConstructor
public final class Dependency {

    private final ConstraintReference reference;
    private final Map<ConstraintStatus, Readiness> readinessMap;

    public static DependencyBuilder builder() {
        return new DependencyBuilder();
    }

}
