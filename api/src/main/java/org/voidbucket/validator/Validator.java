package org.voidbucket.validator;

import org.jetbrains.annotations.NotNull;

public interface Validator<T> {

    @NotNull ValidationReport validate(@NotNull T subject);

}
