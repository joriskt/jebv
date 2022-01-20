package org.voidbucket.validator.reflect.param;

import lombok.Getter;
import org.voidbucket.validator.reflect.param.ParameterProvider;

import java.lang.reflect.Parameter;

@Getter
public final class TypeParameterProvider implements ParameterProvider {

    private final Class<?> type;
    private final Object value;

    public TypeParameterProvider(final Class<?> type, final Object value) {
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
