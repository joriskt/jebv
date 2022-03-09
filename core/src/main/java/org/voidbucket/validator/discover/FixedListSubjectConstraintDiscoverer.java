package org.voidbucket.validator.discover;

import lombok.RequiredArgsConstructor;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintDiscoverer;

import java.util.Set;

/**
 * Provides a static set of {@link Constraint}s upon from the {@link #discoverFromSubject(Class)} method.
 */
@RequiredArgsConstructor
public final class FixedListSubjectConstraintDiscoverer implements ConstraintDiscoverer {

    private final Set<? extends Constraint> constraints;

    @Override
    public Set<? extends Constraint> discoverFromSubject(final Class<?> subject) {
        return constraints;
    }

}
