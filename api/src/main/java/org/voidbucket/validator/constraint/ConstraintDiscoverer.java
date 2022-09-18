package org.voidbucket.validator.constraint;

import java.util.Set;

public interface ConstraintDiscoverer {

    Set<? extends Constraint> discover(final Class<?> subject);

}
