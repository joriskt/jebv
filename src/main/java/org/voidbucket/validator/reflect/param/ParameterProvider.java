package org.voidbucket.validator.reflect.param;

import java.lang.reflect.Parameter;
import java.util.Arrays;

public interface ParameterProvider {
    default boolean canProvide(Parameter[] parameters) {
        return Arrays.stream(parameters).allMatch(this::canProvide);
    }

    boolean canProvide(Parameter parameter);
    Object provide(Parameter parameter);

    static ParameterProviderBuilder builder() {
        return new ParameterProviderBuilder();
    }

    static ParameterProvider combine(ParameterProvider... parameterProviders) {
        return new AggregateParameterProvider(parameterProviders);
    }

}
