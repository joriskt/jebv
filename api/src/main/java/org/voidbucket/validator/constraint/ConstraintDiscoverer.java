package org.voidbucket.validator.constraint;

import java.util.Set;

public interface ConstraintDiscoverer {

    default Set<? extends Constraint> discoverFromValidators(final Set<? extends ConstraintValidator> validators) {
        return Set.of();
    }

    default Set<? extends Constraint> discoverFromSubject(final Class<?> subject) {
        return Set.of();
    }

}
