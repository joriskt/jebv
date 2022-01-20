package org.voidbucket.validator.reflect.param;

import java.util.Objects;

public final class ParameterProviderBuilder {

    private final AggregateParameterProvider parameterProvider;

    public ParameterProviderBuilder() {
        parameterProvider = new AggregateParameterProvider();
    }

    public ParameterProviderBuilder name(final String name, final Object value) {
        Objects.requireNonNull(name);
        parameterProvider.append(new NameParameterProvider(name, value));
        return this;
    }

    public ParameterProviderBuilder type(final Object value) {
        return type(value.getClass(), value);
    }

    public ParameterProviderBuilder type(final Class<?> type, final Object value) {
        Objects.requireNonNull(type);
        parameterProvider.append(new TypeParameterProvider(type, value));
        return this;
    }

    public AggregateParameterProvider build() {
        return parameterProvider;
    }

}
