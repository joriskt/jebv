package org.voidbucket.validator.reflect.param;

import lombok.Getter;

import java.lang.reflect.Parameter;

@Getter
public final class NameParameterProvider implements ParameterProvider {

    private final String name;
    private final Object value;

    public NameParameterProvider(final String name, final Object value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public boolean canProvide(final Parameter parameter) {
        return parameter.getName().equals(name);
    }

    @Override
    public Object provide(final Parameter parameter) {
        return value;
    }
}
