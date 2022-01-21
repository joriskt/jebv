package org.voidbucket.validator.reflect.param;

import java.lang.reflect.Parameter;
import java.util.Arrays;

public interface ParameterBinder {

    default boolean canProvide(Parameter[] parameters) {
        return Arrays.stream(parameters).allMatch(this::canProvide);
    }

    boolean canProvide(Parameter parameter);

    Object provide(Parameter parameter);

    static ParameterProviderBuilder builder() {
        return new ParameterProviderBuilder();
    }

    static ParameterBinder combine(ParameterBinder... parameterBinders) {
        return new AggregateParameterBinder(parameterBinders);
    }

}
