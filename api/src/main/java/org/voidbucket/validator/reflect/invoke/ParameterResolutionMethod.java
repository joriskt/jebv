package org.voidbucket.validator.reflect.invoke;

import org.voidbucket.validator.Context;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;

public interface ParameterResolutionMethod {

    Optional<Object> resolve(Parameter parameter, Method method, Context context);

}
