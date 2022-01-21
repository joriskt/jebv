package org.voidbucket.validator.reflect.param;

import lombok.Getter;

import java.lang.reflect.Parameter;

@Getter
public final class TypeParameterBinder implements ParameterBinder {

    private final Class<?> type;
    private final Object value;

    public TypeParameterBinder(final Class<?> type, final Object value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public boolean canProvide(final Parameter parameter) {
        return type.equals(parameter.getType());
    }

    @Override
    public Object provide(final Parameter parameter) {
        return value;
    }

}
