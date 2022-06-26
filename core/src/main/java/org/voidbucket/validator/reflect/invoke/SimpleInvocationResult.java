package org.voidbucket.validator.reflect.invoke;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public final class SimpleInvocationResult implements InvocationResult {

    private final boolean ok;
    private final Object result;
    private final Throwable throwable;

    private SimpleInvocationResult(final boolean ok,
                                   final Object result,
                                   final Throwable throwable) {
        this.ok = ok;
        if (ok) {
            this.result = result;
            this.throwable = null;
        } else {
            this.result = null;
            this.throwable = throwable;
        }
    }

    @Override
    public Optional<Object> getResult() {
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<Throwable> getThrowable() {
        return Optional.ofNullable(throwable);
    }

    @Override
    public boolean isOk() {
        return ok;
    }

    @Override
    public boolean isError() {
        return !ok;
    }

    public static InvocationResult ofResult(final @NotNull Object result) {
        return new SimpleInvocationResult(true, result, null);
    }

    public static InvocationResult ofThrowable(final @NotNull Throwable throwable) {
        return new SimpleInvocationResult(false, null, throwable);
    }

}
