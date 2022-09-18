package org.voidbucket.validator.constraint.readiness;

import org.voidbucket.validator.ValidatorState;
import org.voidbucket.validator.constraint.ConstraintReference;
import org.voidbucket.validator.constraint.ConstraintStatus;
import org.voidbucket.validator.constraint.Readiness;
import org.voidbucket.validator.constraint.ReadinessEvaluator;

import java.util.ArrayList;
import java.util.List;

public class ChronologyReadinessEvaluator implements ReadinessEvaluator {

    private final List<ConstraintReference> afters;

    public ChronologyReadinessEvaluator() {
        afters = new ArrayList<>();
    }

    public void after(final ConstraintReference reference) {
        afters.add(reference);
    }

    @Override
    public Readiness evaluate(final ValidatorState validatorState) {
        for (ConstraintReference after : afters) {
            final ConstraintStatus constraintStatus = validatorState.getStatus(after);
            if (constraintStatus.isPending()) {
                return Readiness.WAIT;
            }
        }
        return Readiness.READY;
    }

}
