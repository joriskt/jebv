package org.voidbucket.validator.reflect.param;

import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.List;

public class AggregateParameterProvider implements ParameterProvider {

    private final LinkedList<ParameterProvider> children;

    public AggregateParameterProvider(final ParameterProvider... parameterProviders) {
        this.children = new LinkedList<>();
        children.addAll(List.of(parameterProviders));
    }

    public void prepend(final ParameterProvider... parameterProviders) {
        for (ParameterProvider parameterProvider : parameterProviders) {
            this.children.addLast(parameterProvider);
        }
    }

    public void append(final ParameterProvider... parameterProviders) {
        for (ParameterProvider parameterProvider : parameterProviders) {
            this.children.addLast(parameterProvider);
        }
    }

    @Override
    public boolean canProvide(Parameter parameter) {
        return children.stream().anyMatch(parameterProvider -> parameterProvider.canProvide(parameter));
    }

    @Override
    public Object provide(Parameter parameter) {
        return children.stream()
            .filter(parameterProvider -> parameterProvider.canProvide(parameter))
            .findFirst()
            .map(parameterProvider -> parameterProvider.provide(parameter))
            .orElseThrow();
    }
}
