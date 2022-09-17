package org.voidbucket.validator.impl.simple;

import lombok.AccessLevel;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;
import org.voidbucket.validator.ContextFactory;
import org.voidbucket.validator.ValidationReport;
import org.voidbucket.validator.Validator;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.reflect.invoke.ConstraintInvoker;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Builder(access = AccessLevel.PACKAGE)
public final class SimpleValidator<T> implements Validator<T> {

    private final Class<T> subjectType;
    private final List<Constraint> constraints;

    private final ContextFactory contextFactory;

    private final ConstraintInvoker defaultInvoker;
    private final Map<Class<? extends ConstraintInvoker>, ConstraintInvoker> invokerMap;

    @Override
    public @NotNull ValidationReport validate(final @NotNull T subject) {
        Objects.requireNonNull(subject, "subject must not be null");

        final SimpleValidatorRunner runner = new SimpleValidatorRunner(
            subject,
            constraints,
            defaultInvoker,
            invokerMap,
            contextFactory);

        // RUN! Originally was just while (runner.step()) but I wanted to shut up my IDE :^).
        while (runner.hasRemaining()) {
            if (!runner.step()) {
                break;
            }
        }

        return runner.buildReport();
    }

}
