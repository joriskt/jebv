package org.voidbucket.validator.impl;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.voidbucket.validator.Context;
import org.voidbucket.validator.ParameterHolder;
import org.voidbucket.validator.ValidatorState;
import org.voidbucket.validator.violation.Violation;

import java.util.Objects;

@Getter
public final class DefaultContext implements Context {

    private final ValidatorState state;
    private final Object subject;
    private final ParameterHolder params;

    public DefaultContext(final @NotNull ValidatorState state,
                          final @NotNull Object subject) {
        this(state, subject, new DefaultParameterHolder());
    }

    public DefaultContext(final @NotNull ValidatorState state,
                          final @NotNull Object subject,
                          final @NotNull ParameterHolder params) {
        this.state = Objects.requireNonNull(state, "state must not be null");
        this.subject = Objects.requireNonNull(subject, "subject must not be null");
        this.params = Objects.requireNonNull(params, "params must not be null");

        params.put(DefaultContext.class, this);
        params.put(Context.class, this);
    }

    @Override
    public void raise(final @NotNull Violation violation) {
        this.state.raise(Objects.requireNonNull(violation, "violation must not be null"));
    }

}
