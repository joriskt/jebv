package org.voidbucket.validator.discover;

import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintDiscoverer;

import java.util.Set;

/**
 * Provides no constraints.
 */
public final class NoOpConstraintDiscoverer implements ConstraintDiscoverer {

    @Override
    public Set<? extends Constraint> discover(Class<?> subject) {
        return Set.of();
    }

}
