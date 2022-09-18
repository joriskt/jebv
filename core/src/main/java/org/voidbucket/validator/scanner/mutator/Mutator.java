package org.voidbucket.validator.scanner.mutator;

import org.voidbucket.validator.scanner.ConstraintPrototypeMutator;

/**
 *
 */
public @interface Mutator {

    Class<? extends ConstraintPrototypeMutator> appliedBy();

}
