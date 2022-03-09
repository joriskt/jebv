package org.voidbucket.validator.planner.impl;

import org.voidbucket.validator.constraint.ConstraintValidator;
import org.voidbucket.validator.planner.ConstraintSorter;

import java.util.List;
import java.util.Set;

public final class NoOpConstraintSorter<T extends ConstraintValidator> implements ConstraintSorter<T> {

    @Override
    public List<T> sort(final Set<T> constraint) {
        return constraint.stream().toList();
    }

}
