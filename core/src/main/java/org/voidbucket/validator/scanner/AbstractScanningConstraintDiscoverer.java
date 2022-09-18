package org.voidbucket.validator.scanner;

import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintDiscoverer;

import java.util.Set;

public abstract class AbstractScanningConstraintDiscoverer implements ConstraintDiscoverer {

    public AbstractScanningConstraintDiscoverer() {

    }

    @Override
    public Set<? extends Constraint> discover(Class<?> subject) {
        return null;
    }

}
