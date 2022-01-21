package org.voidbucket.validator.reflect.param;

import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.List;

public class AggregateParameterBinder implements ParameterBinder {

    private final LinkedList<ParameterBinder> children;

    public AggregateParameterBinder(final ParameterBinder... parameterBinders) {
        this.children = new LinkedList<>();
        children.addAll(List.of(parameterBinders));
    }

    public void prepend(final ParameterBinder... parameterBinders) {
        for (ParameterBinder parameterBinder : parameterBinders) {
            this.children.addLast(parameterBinder);
        }
    }

    public void append(final ParameterBinder... parameterBinders) {
        for (ParameterBinder parameterBinder : parameterBinders) {
            this.children.addLast(parameterBinder);
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
