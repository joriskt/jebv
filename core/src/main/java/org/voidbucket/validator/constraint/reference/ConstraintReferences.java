package org.voidbucket.validator.constraint.reference;

import org.voidbucket.validator.constraint.ConstraintReference;

public final class ConstraintReferences {

    public static ConstraintReference ofType(final Class<?> type) {
        return new ConstraintTypeReference(type);
    }

    public static ConstraintReference ofName(final String name) {
        return new ConstraintNameReference(name);
    }

}
