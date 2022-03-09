package org.voidbucket.validator.constraint.reference;

import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintReference;

import java.util.Objects;

public final class ConstraintNameReference implements ConstraintReference {

    private final String name;

    public ConstraintNameReference(final String name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public boolean equals(final Constraint constraint) {
        return name.equals(constraint.getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ConstraintNameReference reference) {
            return Objects.equals(name, reference.name);
        }
        return false;
    }

}
