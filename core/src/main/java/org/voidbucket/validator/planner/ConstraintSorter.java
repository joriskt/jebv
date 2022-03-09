package org.voidbucket.validator.planner;

import org.voidbucket.validator.constraint.ConstraintValidator;

import java.util.List;
import java.util.Set;

public interface ConstraintSorter<T extends ConstraintValidator> {

    List<T> sort(Set<T> constraint);

}
