package org.voidbucket.validator.constraint.reference;

import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintReference;

import java.util.Objects;

public final class ConstraintTypeReference implements ConstraintReference {

    private final Class<?> type;

    public ConstraintTypeReference(final Class<?> type) {
        this.type = Objects.requireNonNull(type);
    }

    @Override
    public boolean equals(final Constraint constraint) {
        return type.isAssignableFrom(constraint.getClass());
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ConstraintTypeReference reference) {
            return Objects.equals(type, reference.type);
        }
        return false;
    }

}
