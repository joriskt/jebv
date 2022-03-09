package org.voidbucket.validator.discover;

import lombok.RequiredArgsConstructor;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintDiscoverer;
import org.voidbucket.validator.constraint.ConstraintValidator;

import java.util.Set;

/**
 * Provides a static set of {@link Constraint}s upon from the {@link #discoverFromValidators(Set)} method.
 */
@RequiredArgsConstructor
public final class FixedListValidatorConstraintDiscoverer implements ConstraintDiscoverer {

    private final Set<? extends Constraint> constraints;

    @Override
    public Set<? extends Constraint> discoverFromValidators(final Set<? extends ConstraintValidator> validators) {
        return constraints;
    }

}
