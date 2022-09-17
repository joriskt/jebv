package org.voidbucket.validator.constraint.dependency;

import lombok.Setter;
import lombok.experimental.Accessors;
import org.voidbucket.validator.constraint.ConstraintReference;
import org.voidbucket.validator.constraint.ConstraintStatus;
import org.voidbucket.validator.constraint.Readiness;
import org.voidbucket.validator.constraint.ReadinessEvaluatorChain;

import java.util.HashMap;
import java.util.Map;

@Setter
@Accessors(chain = true, fluent = true)
public final class DependencyBuilder {

    private ConstraintReference reference;
    private Map<ConstraintStatus, Readiness> readinessMap;

    DependencyBuilder() {
        readinessMap = new HashMap<>();
    }

    /**
     * Specify the {@link Readiness} that this {@link Dependency} causes when the {@link ReadinessEvaluatorChain} in the
     * {@link DependencyReadinessEvaluator} is being evaluated.
     * @param status The status to specify a readiness for.
     * @param readiness The readiness to return when this status is inputted.
     * @return this
     */
    public DependencyBuilder map(final ConstraintStatus status,
                                 final Readiness readiness) {
        readinessMap.put(status, readiness);
        return this;
    }

    public Dependency build() {
        return new Dependency(reference, readinessMap);
    }

}
