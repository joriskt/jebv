package org.voidbucket.validator.reflect.invoke;

import org.voidbucket.validator.Context;

import java.lang.reflect.Method;

/**
 * A utility used to invoke methods using the given {@link Context}.
 */
public interface MethodInvoker extends Cloneable {

    Object invoke(Object instance, Method method, Context context)
        throws InvocationSubjectException, InvocationRuntimeException;

}
