package org.voidbucket.validator.impl;

import org.voidbucket.validator.Context;
import org.voidbucket.validator.ContextFactory;
import org.voidbucket.validator.ValidatorState;
import org.voidbucket.validator.constraint.Constraint;

public final class DefaultContextFactory implements ContextFactory {

    @Override
    public Context createValidationContext(final ValidatorState state) {
        return new DefaultContext(state, state.getSubject());
    }

    @Override
    public Context createConstraintContext(final Context parent, final Constraint constraint) {
        return new DefaultContext(parent.getState(), parent.getState().getSubject());
    }

}
