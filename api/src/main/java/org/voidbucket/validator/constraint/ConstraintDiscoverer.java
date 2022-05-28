package org.voidbucket.validator.constraint;

import java.util.Set;

public interface ConstraintDiscoverer {

    default Set<? extends Constraint> discover(final Class<?> subject) {
        return Set.of();
    }

}
