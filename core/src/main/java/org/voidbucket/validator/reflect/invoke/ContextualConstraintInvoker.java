package org.voidbucket.validator.reflect.invoke;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.voidbucket.validator.Context;
import org.voidbucket.validator.ParameterHolder;
import org.voidbucket.validator.exception.MethodInvocationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;

import static org.voidbucket.validator.reflect.invoke.ContextualConstraintInvoker.ParameterResolverMethod.*;

/**
 * A {@link ConstraintInvoker} that uses contextual clues to determine the desired
 */
public final class ContextualConstraintInvoker extends AbstractConstraintInvoker {

    @Getter
    private final ParameterResolverStrategy parameterResolverStrategy;

    /**
     * Constructs this invoker using the default strategy: {@link ParameterResolverStrategy#BY_TYPE_THEN_NAME}.
     */
    public ContextualConstraintInvoker() {
        this(ParameterResolverStrategy.BY_TYPE_THEN_NAME);
    }

    /**
     * Constructs this invoker using the provided {@link ParameterResolverStrategy}.
     * @param parameterResolverStrategy the parameter resolving mode to use
     */
    public ContextualConstraintInvoker(final ParameterResolverStrategy parameterResolverStrategy) {
        this.parameterResolverStrategy = parameterResolverStrategy;
    }

    @Override
    protected Object _invoke(final Object instance, final Method method, final Context context)
        throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Parameter[] parameters = method.getParameters();
        final ParameterHolder holder = context.getParams();

        final Object[] args = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameter param = parameters[i];

            // Resolve the parameter.
            Optional<Object> arg = resolveParameter(param, parameterResolverStrategy, holder);

            // Fail if the argument didn't resolve. (It can have resolved to null!)
            if (arg.isEmpty()) {
                throw new MethodInvocationException(
                    this,
                    String.format(
                        "While invoking method %s#%s, failed to resolve parameter %d of type %s. (strategy = %s)",
                        method.getDeclaringClass().getCanonicalName(),
                        method.getName(),
                        i,
                        param.getType().getCanonicalName(),
                        parameterResolverStrategy));
            }

            args[i] = arg.get();
        }

        // Perform the invocation.
        return method.invoke(instance, args);
    }

    /**
     * Resolve the given {@link Parameter} from the {@link ParameterHolder} using the chosen
     * {@link ParameterResolverStrategy}.
     * @param param The parameter to resolve.
     * @param resolverStrategy The resolver strategy to use.
     * @param holder The parameter holder.
     * @return The resolved parameter, if any, as an {@link Optional}.
     */
    private Optional<Object> resolveParameter(final Parameter param,
                                              final ParameterResolverStrategy resolverStrategy,
                                              final ParameterHolder holder) {
        Optional<Object> object = Optional.empty();
        for (ParameterResolverMethod resolverMethod : resolverStrategy.getMethods()) {
            object = resolveUsingMethod(param.getType(), param.getName(), resolverMethod, holder);
            if (object.isPresent()) {
                break;
            }
        }
        return object;
    }

    /**
     * Given the <b>type</b> and <b>name</b> of a {@link Parameter} and a method to resolve it with,
     * attempt to resolve the {@link Parameter} from the {@link ParameterHolder}.
     * @param type The type of the parameter.
     * @param name The name of the parameter.
     * @param method The resolution method to use.
     * @param holder The parameter holder to apply the resolution method to.
     * @return The resolved parameter, if any, as an {@link Optional}.
     */
    private Optional<Object> resolveUsingMethod(final Class<?> type,
                                                final String name,
                                                final ParameterResolverMethod method,
                                                final ParameterHolder holder) {
        return switch (method) {
            case TYPE -> holder.has(type) ? Optional.of(holder.get(type)) : Optional.empty();
            case NAME -> holder.has(name) ? Optional.of(holder.get(name)) : Optional.empty();
            case NONE -> Optional.empty();
        };
    }

    /**
     * The strategy to use when resolving {@link Parameter}s when invoking a {@link Method}.
     */
    @Getter
    @RequiredArgsConstructor
    public enum ParameterResolverStrategy {
        /**
         * The parameter is resolved by <b>type</b> alone.
         */
        BY_TYPE(TYPE, NONE),

        /**
         * The parameter is resolved by <b>name</b> alone.
         */
        BY_NAME(NAME, NONE),

        /**
         * The parameter is resolved by <b>type</b> first and by <b>name</b> second if the first resolve failed.
         */
        BY_TYPE_THEN_NAME(TYPE, NAME),

        /**
         * The parameter is resolved by <b>name</b> first and by <b>type</b> second if the first resolve failed.
         */
        BY_NAME_THEN_TYPE(NAME, TYPE);

        private final ParameterResolverMethod firstMethod;
        private final ParameterResolverMethod secondMethod;

        ParameterResolverMethod[] getMethods() {
            return new ParameterResolverMethod[] { firstMethod, secondMethod };
        }

    }

    /**
     * The way by which to resolve the value of a single {@link Parameter}.
     */
    enum ParameterResolverMethod {
        NAME,
        TYPE,
        NONE,
    }

}
