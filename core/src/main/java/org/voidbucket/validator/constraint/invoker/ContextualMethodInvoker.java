package org.voidbucket.validator.constraint.invoker;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.voidbucket.validator.Context;
import org.voidbucket.validator.ParameterHolder;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.exception.ConstraintInvocationException;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static org.voidbucket.validator.constraint.invoker.ContextualMethodInvoker.ParameterResolverMethod.*;

public final class ContextualMethodInvoker extends AbstractMethodInvoker {

    @Getter
    private final ParameterResolverStrategy parameterResolverStrategy;

    /**
     * Constructs this invoker using the default strategy: {@link ParameterResolverStrategy#BY_TYPE_THEN_NAME}.
     */
    public ContextualMethodInvoker() {
        this(ParameterResolverStrategy.BY_TYPE_THEN_NAME);
    }

    /**
     * Constructs this invoker using the provided {@link ParameterResolverStrategy}.
     *
     * @param parameterResolverStrategy the parameter resolving mode to use
     */
    public ContextualMethodInvoker(final ParameterResolverStrategy parameterResolverStrategy) {
        this.parameterResolverStrategy = parameterResolverStrategy;
    }

    @Override
    protected Object _invoke(final Constraint constraint, final Context context) throws Throwable {
        final Method method = constraint.getMethod();
        final Parameter[] parameters = method.getParameters();
        final ParameterHolder holder = context.getParams();

        final Object[] args = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameter param = parameters[i];

            // Fetch the argument.
            Object arg = fetch(param, parameterResolverStrategy, holder);

            // Fail if the argument is still null.
            if (arg == null) {
                throw new ConstraintInvocationException(
                    constraint, this,
                    String.format("While invoking method %s#%s, failed to resolve parameter %d of type %s. (mode = %s)",
                                  method.getDeclaringClass().getCanonicalName(),
                                  method.getName(), i, param.getType().getCanonicalName(),
                                  parameterResolverStrategy));
            }

            args[i] = arg;
        }

        // Perform the invocation.
        return method.invoke(constraint, args);
    }

    private Object fetch(final Parameter param,
                         final ParameterResolverStrategy resolverStrategy,
                         final ParameterHolder holder) {
        Object object = null;
        for (ParameterResolverMethod resolverMethod : resolverStrategy.getMethods()) {
            object = fetchUsingMethod(param.getType(), param.getName(), resolverMethod, holder);
            if (object != null) {
                break;
            }
        }
        return object;
    }

    private Object fetchUsingMethod(final Class<?> type,
                                    final String name,
                                    final ParameterResolverMethod method,
                                    final ParameterHolder holder) {
        return switch (method) {
            case TYPE -> holder.get(type);
            case NAME -> holder.get(name);
            case NONE -> null;
        };
    }

    @Getter
    @RequiredArgsConstructor
    public enum ParameterResolverStrategy {
        BY_TYPE(TYPE, NONE),
        BY_NAME(NAME, NONE),
        BY_TYPE_THEN_NAME(TYPE, NAME),
        BY_NAME_THEN_TYPE(NAME, TYPE);

        private final ParameterResolverMethod firstMethod;
        private final ParameterResolverMethod secondMethod;

        public ParameterResolverMethod[] getMethods() {
            return new ParameterResolverMethod[] { firstMethod, secondMethod };
        }

    }

    enum ParameterResolverMethod {
        NAME,
        TYPE,
        NONE,
    }

}
