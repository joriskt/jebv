package org.voidbucket.validator.reflect.param;

import java.lang.reflect.Parameter;

public final class NullParameterProvider implements ParameterProvider {
    @Override
    public boolean canProvide(final Parameter parameter) {
        return false;
    }

    @Override
    public Object provide(final Parameter parameter) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
