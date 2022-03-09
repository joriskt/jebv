package org.voidbucket.validator.planner;

import org.jetbrains.annotations.NotNull;
import org.voidbucket.validator.ValidationReport;
import org.voidbucket.validator.Validator;

public class PlanningValidator<T> implements Validator<T> {

    private final Plan plan;

    PlanningValidator(final Plan plan) {
        this.plan = plan;
    }

    @Override
    public @NotNull ValidationReport validate(@NotNull T subject) {
        return null;
    }

}
