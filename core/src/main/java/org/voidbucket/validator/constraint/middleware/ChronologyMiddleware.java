package org.voidbucket.validator.constraint.middleware;

import org.voidbucket.validator.ValidatorState;
import org.voidbucket.validator.constraint.ConstraintReadiness;
import org.voidbucket.validator.constraint.ConstraintMiddleware;
import org.voidbucket.validator.constraint.ConstraintReference;
import org.voidbucket.validator.constraint.ConstraintStatus;

import java.util.ArrayList;
import java.util.List;

public class ChronologyMiddleware implements ConstraintMiddleware {

    private final List<ConstraintReference> afters;

    public ChronologyMiddleware() {
        afters = new ArrayList<>();
    }

    public void after(final ConstraintReference reference) {
        afters.add(reference);
    }

    @Override
    public ConstraintReadiness evaluate(final ValidatorState validatorState) {
        for (ConstraintReference after : afters) {
            final ConstraintStatus constraintStatus = validatorState.getStatus(after);
            if (constraintStatus.isPending()) {
                return ConstraintReadiness.WAIT;
            }
        }
        return ConstraintReadiness.READY;
    }

}
