package org.voidbucket.validator;

import org.jetbrains.annotations.NotNull;

public interface ValidatorFactory {
    <T> @NotNull Validator<T> createValidator(@NotNull Class<T> subjectType);
}
