package org.voidbucket.validator.discover;

import lombok.Builder;
import lombok.Singular;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintDiscoverer;

import java.util.Set;

/**
 * Provides a static set of {@link Constraint}s from the {@link #discover(Class)} method.
 */
@Builder(builderClassName = "Builder")
public final class FixedListConstraintDiscoverer implements ConstraintDiscoverer {

    @Singular
    private final Set<? extends Constraint> constraints;

    public FixedListConstraintDiscoverer(final Set<? extends Constraint> constraints) {
        this.constraints = constraints;
    }

    public FixedListConstraintDiscoverer(final Constraint ...constraints) {
        this.constraints = Set.of(constraints);
    }

    @Override
    public Set<? extends Constraint> discover(final Class<?> subject) {
        return constraints;
    }

}
